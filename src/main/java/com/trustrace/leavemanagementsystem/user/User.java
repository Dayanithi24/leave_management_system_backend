package com.trustrace.leavemanagementsystem.user;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
    private LocalDate dateOfBirth;

    private String[] roles;
    private String designation;
    private String department;
    private LocalDate dateOfJoining;

    private String managerId;

    private List<String> teamMembers;
    private Map<String, Object> myFiles;
}
