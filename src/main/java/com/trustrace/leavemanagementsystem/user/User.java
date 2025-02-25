package com.trustrace.leavemanagementsystem.user;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "users")
@Data

public class User {
    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String gender;
    private String phoneNumber;
    private String location;
    private LocalDate dateOfBirth;  // Age

    private String[] roles;
    private String designation;
    private String department;
    private LocalDate dateOfJoining;

    private float remainingCasualLeaves;
    private float remainingSickLeaves;
    private float remainingWorkFromHome;
    private float totalPermissionHours;
    private float usedPermissionHours = 0;
    private float lossOfPayDays = 0;

    private String managerId;
    private int warningsCount = 0;
    private int leaveApprovalCount = 0;

    private List<String> teamMembers;
}
