package com.trustrace.leavemanagementsystem.leave;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LeaveDto {
    private String id;
    private String title;
    private String start;
    private String end;
    private boolean allDay;
}
