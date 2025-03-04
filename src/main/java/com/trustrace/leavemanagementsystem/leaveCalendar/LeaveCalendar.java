package com.trustrace.leavemanagementsystem.leaveCalendar;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "leave_calendar")
@Data
@Builder
public class LeaveCalendar {
    @Id
    private String id;
    private int year;
    private List<LeaveDay> leaveDays;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean status;
}
