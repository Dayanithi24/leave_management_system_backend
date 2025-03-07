package com.trustrace.leavemanagementsystem.leavepolicy;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LeavePolicyDto {
    private String id;
    private String startDate;
    private String endDate;
    private List<LeaveType> leaves;
    private String previousPolicyId;
    private boolean active;
}
