package com.trustrace.leavemanagementsystem.leavepolicy;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "leave_policies")
@Data
@Builder
public class LeavePolicy {

    @Id
    private String id;
    private List<LeaveType> leaves;
    private String createdBy;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant startDate;
    private Instant endDate;
    private String previousPolicyId;
    private boolean active;
}
