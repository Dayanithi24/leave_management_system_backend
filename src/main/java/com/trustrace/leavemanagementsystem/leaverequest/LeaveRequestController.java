package com.trustrace.leavemanagementsystem.leaverequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("v1/api/leave-request/")
public class LeaveRequestController {
    @Autowired
    private LeaveRequestService lrs;

    @PostMapping
    public ResponseEntity<LeaveRequest> createLeaveRequest(@RequestBody LeaveRequest leaveRequest, @RequestParam List<MultipartFile> files) throws Exception {
        if (leaveRequest == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        LeaveRequest leaveRequest1 = lrs.createLeaveRequest(leaveRequest, files);
        return ResponseEntity.ok(leaveRequest);
    }

    @GetMapping
    public ResponseEntity<List<LeaveRequest>> getAllLeaveRequests() {
        return ResponseEntity.ok(lrs.getAllLeaveRequests());
    }

    @GetMapping("{id}")
    public ResponseEntity<LeaveRequest> getLeaveRequestById(@PathVariable String id) {
        if(id == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        LeaveRequest leaveRequest = lrs.getLeaveRequestById(id);
        if(leaveRequest == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.ok(leaveRequest);
    }

    @PutMapping("approve/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HR', 'ROLE_MANAGER')")
    public ResponseEntity<LeaveRequest> approveLeaveRequest(@PathVariable String id){
        if(id == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        LeaveRequest leaveRequest = lrs.approveLeaveRequest(id);
        if(leaveRequest == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.ok(leaveRequest);
    }

    @PutMapping("reject/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HR', 'ROLE_MANAGER')")
    public ResponseEntity<LeaveRequest> rejectLeaveRequest(@PathVariable String id, @RequestBody String reason){
        if(id == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        LeaveRequest leaveRequest = lrs.rejectLeaveRequest(id, reason);
        if(leaveRequest == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.ok(leaveRequest);
    }

    @PutMapping("cancel/{leaveId}")
    public ResponseEntity<String> cancelLeaveRequest(@PathVariable String leaveId, @RequestParam String userId) {
        String response = lrs.cancelLeaveRequest(leaveId, userId);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Leave request not found");
        }
        else if (response.equals("Unauthorized")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized to cancel this leave request");
        }
        if (response.equals("success")) {
            return ResponseEntity.ok("Leave request cancelled successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Approved leave cannot be cancelled directly");
    }

    @PutMapping("request-cancel/{leaveId}")
    public ResponseEntity<String> requestLeaveCancellation(@PathVariable String leaveId,
                                                           @RequestParam String userId,
                                                           @RequestParam String reason) {

        String response = lrs.requestCancelLeave(leaveId, userId, reason);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Leave request not found");
        }
        if (response.equals("Unauthorized")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized to request cancellation");
        }
        if (response.equals("bad")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Only approved leave can be requested for cancellation");
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("approve-cancel/{leaveId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HR', 'ROLE_MANAGER')")
    public ResponseEntity<String> approveLeaveCancellation(@PathVariable String leaveId,
                                                           @RequestParam String managerId) {
        String response = lrs.approveCancelLeave(leaveId, managerId);

        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Leave request not found");
        }
        if (response.equals("Unauthorized")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only the manager can approve cancellation");
        }
        if (response.equals("bad")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No cancellation request found");
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteLeaveRequest(@PathVariable String id){
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id is null");
        if(lrs.deleteLeaveRequest(id)) {
            return ResponseEntity.ok("Deleted Successfully!!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Leave Policy not found");
    }
}
