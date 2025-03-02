package com.trustrace.leavemanagementsystem.leaverequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LeaveRequestDao {
    @Autowired
    private MongoTemplate mt;

    public LeaveRequest createLeaveRequest(LeaveRequest leaveRequest) {
        return mt.save(leaveRequest);
    }

    public List<LeaveRequest> getAllLeavePolicies() {
        return mt.findAll(LeaveRequest.class);
    }

    public LeaveRequest getLeaveRequestById(String id) {
        return mt.findById(id, LeaveRequest.class);
    }

    public boolean deleteLeaveRequest(String id) {
        LeaveRequest leaveRequest = getLeaveRequestById(id);
        if(leaveRequest == null) return false;
        mt.remove(leaveRequest);
        return true;
    }

    public LeaveRequest saveLeaveRequest(LeaveRequest leaveRequest){
       return mt.save(leaveRequest);
    }
}
