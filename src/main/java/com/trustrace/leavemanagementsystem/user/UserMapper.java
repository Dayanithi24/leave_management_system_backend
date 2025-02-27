package com.trustrace.leavemanagementsystem.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toUserDto(User user, User manager) {
        UserDto dto = new UserDto();

        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setGender(user.getGender());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setLocation(user.getLocation());
        dto.setDateOfBirth(user.getDateOfBirth());

        dto.setDesignation(user.getDesignation());
        dto.setDepartment(user.getDepartment());
        dto.setDateOfJoining(user.getDateOfJoining());

        dto.setRemainingCasualLeaves(user.getRemainingCasualLeaves());
        dto.setRemainingSickLeaves(user.getRemainingSickLeaves());
        dto.setRemainingWorkFromHome(user.getRemainingWorkFromHome());
        dto.setTotalPermissionHours(user.getTotalPermissionHours());
        dto.setUsedPermissionHours(user.getUsedPermissionHours());
        dto.setLossOfPayDays(user.getLossOfPayDays());

        dto.setWarningsCount(user.getWarningsCount());
        dto.setLeaveApprovalCount(user.getLeaveApprovalCount());
        dto.setTeamMembers(user.getTeamMembers());
        dto.setMyFiles(user.getMyFiles());

        if (manager != null) {
            dto.setManager(new ManagerDto(
                    manager.getId(),
                    manager.getFirstName(),
                    manager.getLastName(),
                    manager.getDesignation(),
                    manager.getDepartment()
            ));
        }

        return dto;
    }
}
