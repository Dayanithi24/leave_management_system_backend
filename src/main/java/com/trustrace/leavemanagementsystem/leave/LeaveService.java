package com.trustrace.leavemanagementsystem.leave;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveService {
    @Autowired
    private LeaveDao dao;

    private List<LeaveDto> convertListToDto(List<Leave> leaves, String timezone) {
        return  leaves.stream().map( leave -> new LeaveDto(
                leave.getId(),
                leave.getTitle(),
                convertToLocalTime(leave.getStart(), timezone),
                convertToLocalTime(leave.getEnd(), timezone),
                leave.isAllDay()
        )).collect(Collectors.toList());
    }

    private LeaveDto convertToDto(Leave leave, String timezone) {
        return LeaveDto.builder()
                .id(leave.getId())
                .title(leave.getTitle())
                .start(convertToLocalTime(leave.getStart(), timezone))
                .end(convertToLocalTime(leave.getEnd(), timezone))
                .allDay(leave.isAllDay())
                .build();
    }

    private String convertToLocalTime(Instant utcTime, String timezone) {
        return ZonedDateTime.ofInstant(utcTime, ZoneId.of(timezone))
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public List<LeaveDto> getAllLeaves(String timezone) {
        List<Leave> leaves = dao.getAllLeaves();
        return convertListToDto(leaves, timezone);
    }


    public List<LeaveDto> getLeavesForMonth(int year, int month, String timezone) {
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

        Instant startInstant = startOfMonth.atStartOfDay(ZoneId.of("UTC")).toInstant();
        Instant endInstant = endOfMonth.atTime(23, 59, 59).atZone(ZoneId.of("UTC")).toInstant();
        List<Leave> leaves = dao.getLeavesForMonth(startInstant, endInstant);

        return convertListToDto(leaves, timezone);
    }

    public LeaveDto saveLeave(Leave leave, String timezone) {
        leave.setCreatedAt(Instant.now());
        Leave leave1 = dao.saveLeave(leave);
        return convertToDto(leave1, timezone);
    }

    public boolean deleteLeave(String id) {
        return dao.deleteLeave(id);
    }
}
