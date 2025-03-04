package com.trustrace.leavemanagementsystem.leaveCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveCalendarService {
    @Autowired
    private LeaveCalendarDao dao;
}
