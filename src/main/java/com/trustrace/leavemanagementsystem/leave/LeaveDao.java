package com.trustrace.leavemanagementsystem.leave;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public class LeaveDao {
    @Autowired
    private MongoTemplate mt;

    public List<Leave> getAllLeaves() {
        return mt.findAll(Leave.class);
    }

    public Leave getLeaveById(String id) {
        return mt.findById(id, Leave.class);
    }

    public List<Leave> getLeavesForMonth(Instant startInstant, Instant endInstant) {
        Query query = new Query(Criteria.where("start").gte(startInstant).and("end").lte(endInstant));
        return mt.find(query, Leave.class);
    }

    public Leave saveLeave(Leave leave) {
        return mt.save(leave);
    }

    public boolean deleteLeave(String id) {
        Leave leave = getLeaveById(id);
        if(leave == null) return false;
        mt.remove(leave);
        return true;
    }
}
