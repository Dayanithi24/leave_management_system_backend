package com.trustrace.leavemanagementsystem.leave;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "leaves")
@Data
@Builder
public class Leave {
    @Id
    private String id;
    private String title;
    private Instant start;
    private Instant end;
    private boolean allDay;
    private String createdBy;
    private Instant createdAt;
}
