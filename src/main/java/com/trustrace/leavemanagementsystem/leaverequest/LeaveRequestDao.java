package com.trustrace.leavemanagementsystem.leaverequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class LeaveRequestDao {
    @Autowired
    private MongoTemplate mt;

    public LeaveRequest createLeaveRequest(LeaveRequest leaveRequest) {
        return mt.save(leaveRequest);
    }

    public List<LeaveRequest> getAllLeavePolicies(String id) {
        Query query = new Query(Criteria.where("requesterId").is(id));
        return mt.find(query, LeaveRequest.class);
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

    public Float getLeavesCount(String id, Date start, Date end) {
//        Query query = new Query(Criteria.where("requesterId").is(id));
//        query.addCriteria(Criteria.where("startDate").gte(start).orOperator(Criteria.where("endDate").lte(end)));
//        List<LeaveRequest> leaveRequests = mt.find(query, LeaveRequest.class);
//
//
//        leaveRequests.forEach(leaveRequest -> {
//            float c = 0;
//            for(String date : leaveRequest.getLeaveDays().keySet()){
//                if(new Date(date) >= start && new Date(end) <= end) {
//                    if(leaveRequest.getLeaveDays().get(date)) {
//                        c += 0.5;
//                    }
//                    else c += 1;
//                }
//            }
//        });
        return Float.valueOf(0);
    }

    public List<LeaveRequest> getAllLeaveRequestsOfMyTeam(String userId) {
        Query query = new Query(Criteria.where("approverId").is(userId));
        return mt.find(query, LeaveRequest.class);
    }
}
