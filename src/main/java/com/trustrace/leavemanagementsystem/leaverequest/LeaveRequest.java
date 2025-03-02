package com.trustrace.leavemanagementsystem.leaverequest;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    private boolean isStartHalfDay;
    private boolean isEndHalfDay;
    private String halfDayType;
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
    private LocalDateTime requestedAt;
    private LocalDateTime updatedAt;
}
