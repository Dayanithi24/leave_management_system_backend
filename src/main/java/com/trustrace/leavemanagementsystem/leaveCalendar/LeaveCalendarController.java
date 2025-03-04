package com.trustrace.leavemanagementsystem.leaveCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/leave-calendar/")
public class LeaveCalendarController {
    @Autowired
    private LeaveCalendarService lcs;

}
