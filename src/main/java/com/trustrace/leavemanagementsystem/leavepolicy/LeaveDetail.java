package com.trustrace.leavemanagementsystem.leavepolicy;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LeaveDetail {
    private String duration;
    private int noOfDays;
}
