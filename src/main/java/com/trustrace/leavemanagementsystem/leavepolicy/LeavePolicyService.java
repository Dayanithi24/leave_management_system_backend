package com.trustrace.leavemanagementsystem.leavepolicy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeavePolicyService {
    @Autowired
    private LeavePolicyDao dao;

    public LeavePolicy createLeavePolicy(LeavePolicy leavePolicy) {
        return dao.createLeavePolicy(leavePolicy);
    }

    public List<LeavePolicy> getAllLeavePolicies() {
        return dao.getAllLeavePolicies();
    }

    public LeavePolicy getLeavePolicyById(String id) {
        return dao.getLeavePolicyById(id);
    }

    public boolean deleteLeavePolicy(String id) {
        return dao.deleteLeavePolicy(id);
    }
}
