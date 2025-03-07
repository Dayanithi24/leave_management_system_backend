package com.trustrace.leavemanagementsystem.leavepolicy;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LeaveType {
    private String name;
    private List<LeaveDetail> details;
}
