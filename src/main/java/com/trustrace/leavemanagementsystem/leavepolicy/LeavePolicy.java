package com.trustrace.leavemanagementsystem.leavepolicy;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "leave_policies")
@Data
public class LeavePolicy {

    @Id
    private String id;

    private int casualLeavePerMonth;
    private int casualLeavePerYear;
    private int minimumDaysToGetCasualLeave;
    private int lossOfPayPerMonth;
    private int lossOfPayPerYear;
    private int sickLeavePerMonth;
    private int sickLeavePerYear;
    private int workFromHomePerMonth;
    private int workFromHomePerWeek;
    private int warningsBeforeEscalation;

    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDate startDate;
    private LocalDate endDate;
}
