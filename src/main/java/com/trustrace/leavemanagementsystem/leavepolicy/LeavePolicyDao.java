package com.trustrace.leavemanagementsystem.leavepolicy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LeavePolicyDao {
    @Autowired
    private MongoTemplate mt;

    public LeavePolicy createLeavePolicy(LeavePolicy leavePolicy) {
        return mt.save(leavePolicy);
    }

    public List<LeavePolicy> getAllLeavePolicies() {
        return mt.findAll(LeavePolicy.class);
    }

    public LeavePolicy getLeavePolicyById(String id) {
        return mt.findById(id, LeavePolicy.class);
    }

    public boolean deleteLeavePolicy(String id) {
        LeavePolicy leavePolicy = getLeavePolicyById(id);
        if(leavePolicy == null) return false;
        mt.remove(leavePolicy);
        return true;
    }
}
