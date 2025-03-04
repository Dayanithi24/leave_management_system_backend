package com.trustrace.leavemanagementsystem.leaveCalendar;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class LeaveDay {
    private LocalDate date;
    private String leaveName;
}
