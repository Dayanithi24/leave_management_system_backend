package com.trustrace.leavemanagementsystem.leavepolicy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeavePolicyService {
    @Autowired
    private LeavePolicyDao dao;

    private String convertToLocalTime(Instant utcTime, String timezone) {
        return ZonedDateTime.ofInstant(utcTime, ZoneId.of(timezone))
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public LeavePolicyDto convertToTimezone(LeavePolicy leavePolicy, String timezone){
        return LeavePolicyDto.builder()
                .id(leavePolicy.getId())
                .leaves(leavePolicy.getLeaves())
                .active(leavePolicy.isActive())
                .startDate(convertToLocalTime(leavePolicy.getStartDate(), timezone))
                .endDate(convertToLocalTime(leavePolicy.getEndDate(), timezone))
                .previousPolicyId(leavePolicy.getPreviousPolicyId())
                .build();
    }

    public LeavePolicyDto createLeavePolicy(LeavePolicy leavePolicy, String userId, String timezone) {
        leavePolicy.setCreatedBy(userId);
        leavePolicy.setActive(false);
        Instant startInstant = leavePolicy.getStartDate()
                .truncatedTo(ChronoUnit.DAYS);
        Instant endInstant = leavePolicy.getEndDate()
                .plus(1, ChronoUnit.DAYS)
                .truncatedTo(ChronoUnit.DAYS)
                .minus(1, ChronoUnit.MILLIS);
        leavePolicy.setStartDate(startInstant);
        leavePolicy.setEndDate(endInstant);
        leavePolicy.setCreatedAt(Instant.now());
        LeavePolicy leavePolicy1 = dao.createLeavePolicy(leavePolicy);

        return convertToTimezone(leavePolicy1, timezone);
    }

    public List<LeavePolicyDto> getAllLeavePolicies(String timezone) {
        List<LeavePolicy> leavePolicies = dao.getAllLeavePolicies();
        return leavePolicies
                .stream()
                .map((leavePolicy -> convertToTimezone(leavePolicy,timezone)))
                .collect(Collectors.toList());

    }

    public LeavePolicyDto getLeavePolicyById(String id, String timezone) {
        LeavePolicy leavePolicy = dao.getLeavePolicyById(id);
        return convertToTimezone(leavePolicy, timezone);
    }

    public boolean deleteLeavePolicy(String id) {
        return dao.deleteLeavePolicy(id);
    }
}
