package com.trustrace.leavemanagementsystem.leave;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/leave/")
public class LeaveController {
    @Autowired
    private LeaveService ls;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HR')")
    public ResponseEntity<LeaveDto> createLeave(@RequestBody Leave leave, @RequestParam String timezone){
        if(leave == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        return ResponseEntity.ok(ls.saveLeave(leave, timezone));
    }

    @GetMapping
    public ResponseEntity<List<LeaveDto>> getAllLeaves(@RequestParam String timezone) {
        return ResponseEntity.ok(ls.getAllLeaves(timezone));
    }

    @GetMapping("/month")
    public ResponseEntity<List<LeaveDto>> getLeavesForMonth(@RequestParam int year, @RequestParam int month, @RequestParam String timezone) {
        return ResponseEntity.ok(ls.getLeavesForMonth(year, month, timezone));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HR')")
    public ResponseEntity<String> deleteLeave(@PathVariable String id) {
        if(id == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id is null");
        boolean response = ls.deleteLeave(id);
        if(!response) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found");
        return ResponseEntity.ok("Deleted Successfully!!");
    }

}
