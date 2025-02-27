package com.trustrace.leavemanagementsystem.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ManagerDto {
    private String id;
    private String firstName;
    private String lastName;
    private String designation;
    private String department;
}
