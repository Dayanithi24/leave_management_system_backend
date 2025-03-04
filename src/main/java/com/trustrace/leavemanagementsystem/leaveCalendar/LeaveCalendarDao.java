package com.trustrace.leavemanagementsystem.leaveCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LeaveCalendarDao {
    @Autowired
    private MongoTemplate mt;
}
