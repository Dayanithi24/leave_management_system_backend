package com.trustrace.leavemanagementsystem.leaverequest;

import com.trustrace.leavemanagementsystem.file.FileData;
import com.trustrace.leavemanagementsystem.file.FileDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class LeaveRequestService {
    @Autowired
    private LeaveRequestDao dao;
    @Autowired
    private FileDataDao fileDao;

    public LeaveRequest createLeaveRequest(LeaveRequest leaveRequest, List<MultipartFile> files) throws Exception {
        leaveRequest.setStatus("PENDING");
        leaveRequest.setRequestedAt(Instant.now());
        leaveRequest.setCancellationRequested(false);
        List<String> documents = new ArrayList<>();
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                FileData fileData = fileDao.uploadFile(file);
                documents.add(fileData.getId());
            }
            leaveRequest.setDocuments(documents);
        }
        return dao.createLeaveRequest(leaveRequest);
    }

    public List<LeaveRequest> getAllLeaveRequests(String id) {
        return dao.getAllLeavePolicies(id);
    }

    public LeaveRequest getLeaveRequestById(String id) {
        return dao.getLeaveRequestById(id);
    }

    public boolean deleteLeaveRequest(String id) {
        return dao.deleteLeaveRequest(id);
    }

    public LeaveRequest approveLeaveRequest(String id) {
        LeaveRequest leaveRequest = dao.getLeaveRequestById(id);
        if (leaveRequest == null) return null;
        leaveRequest.setStatus("APPROVED");
        leaveRequest.setUpdatedAt(Instant.now());
        return dao.saveLeaveRequest(leaveRequest);
    }

    public LeaveRequest rejectLeaveRequest(String id, String reason) {
        LeaveRequest leaveRequest = dao.getLeaveRequestById(id);
        if (leaveRequest == null) return null;
        leaveRequest.setStatus("REJECTED");
        leaveRequest.setRejectionReason(reason);
        leaveRequest.setUpdatedAt(Instant.now());
        return dao.saveLeaveRequest(leaveRequest);
    }

    public String cancelLeaveRequest(String id, String userId) {
        LeaveRequest leaveRequest = dao.getLeaveRequestById(id);
        if (leaveRequest == null) return null;
        if (!leaveRequest.getRequesterId().equals(userId)) {
            return "Unauthorized";
        }
        if("PENDING".equals(leaveRequest.getStatus())){
            leaveRequest.setStatus("CANCELLED");
            leaveRequest.setUpdatedAt(Instant.now());
            leaveRequest.setCancelledBy(userId);
            dao.saveLeaveRequest(leaveRequest);
            return "success";
        }
        return "";
    }

    public String requestCancelLeave(String id, String userId, String reason) {
        LeaveRequest leaveRequest = dao.getLeaveRequestById(id);
        if (leaveRequest == null) return null;
        if (!leaveRequest.getRequesterId().equals(userId))  return "Unauthorized";
        if(!"APPROVED".equals(leaveRequest.getStatus())) return "bad";
        leaveRequest.setCancellationRequested(true);
        leaveRequest.setCancellationReason(reason);
        leaveRequest.setUpdatedAt(Instant.now());
        dao.saveLeaveRequest(leaveRequest);
        return "Cancellation request sent to manager";
    }

    public String approveCancelLeave(String id, String managerId) {
        LeaveRequest leaveRequest = dao.getLeaveRequestById(id);
        if (leaveRequest == null) return null;
        if (!leaveRequest.getApproverId().equals(managerId)) return "Unauthorized";
        if(!leaveRequest.isCancellationRequested()) return "bad";
        leaveRequest.setStatus("CANCELLED");
        leaveRequest.setCancellationRequested(false);
        leaveRequest.setCancelledBy(managerId);
        leaveRequest.setUpdatedAt(Instant.now());
        dao.saveLeaveRequest(leaveRequest);
        return "Leave request successfully cancelled by manager";

    }

    public Float getLeavesCount(String id, Date start, Date end) {
        return dao.getLeavesCount(id, start, end);
    }

    public List<LeaveRequest> getAllLeaveRequestsOfMyTeam(String userId) {
        return dao.getAllLeaveRequestsOfMyTeam(userId);
    }
}
