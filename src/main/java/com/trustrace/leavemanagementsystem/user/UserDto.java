package com.trustrace.leavemanagementsystem.user;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
public class UserDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String phoneNumber;
    private String location;
    private LocalDate dateOfBirth;

    private String[] roles;
    private String designation;
    private String department;
    private LocalDate dateOfJoining;

    private List<String> teamMembers;
    private Map<String, Object> myFiles;

    private ManagerDto manager;
}
