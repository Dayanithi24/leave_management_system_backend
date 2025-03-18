package com.trustrace.leavemanagementsystem.leaverequest;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Document(collection = "leave_requests")
@Data
@Builder
public class LeaveRequest {
    @Id
    private String id;
    private String leavePolicyId;
    private String leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private Map<String, Boolean> leaveDays;
    private float duration;
    private String requesterId;
    private String approverId;
    private boolean cancellationRequested;
    private String cancellationReason;
    private String cancelledBy;
    private String status;
    private String requestReason;
    private String rejectionReason;
    private List<String> documents;
    private Instant requestedAt;
    private Instant updatedAt;
}
