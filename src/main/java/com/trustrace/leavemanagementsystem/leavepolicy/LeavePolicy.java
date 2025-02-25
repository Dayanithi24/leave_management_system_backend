package com.trustrace.leavemanagementsystem.leavepolicy;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "leave_policies")
@Data
public class LeavePolicy {

    @Id
    private String id;

    private int casualLeavePerMonth;
    private int sickLeavePerYear;
    private int workFromHomeLimitPerMonth;
    private int permissionHoursPerMonth;
    private int warningsBeforeEscalation;
}
