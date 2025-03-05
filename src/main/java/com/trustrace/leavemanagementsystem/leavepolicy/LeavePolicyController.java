package com.trustrace.leavemanagementsystem.leavepolicy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("v1/api/leave-policy/")
public class LeavePolicyController {
    @Autowired
    private LeavePolicyService lps;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HR')")
    public ResponseEntity<LeavePolicy> createLeavePolicy(@RequestBody LeavePolicy leavePolicy) {
        if (leavePolicy == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        LeavePolicy leavePolicy1 = lps.createLeavePolicy(leavePolicy);
        return ResponseEntity.ok(leavePolicy1);
    }

    @GetMapping
    public ResponseEntity<List<LeavePolicy>> getAllLeavePolicies() {
        return ResponseEntity.ok(lps.getAllLeavePolicies());
    }

    @GetMapping("{id}")
    public ResponseEntity<LeavePolicy> getLeavePolicyById(@PathVariable String id) {
        if(id == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        LeavePolicy leavePolicy = lps.getLeavePolicyById(id);
        if(leavePolicy == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.ok(leavePolicy);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteLeavePolicy(@PathVariable String id){
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id is null");
        if(lps.deleteLeavePolicy(id)) {
            return ResponseEntity.ok("Deleted Successfully!!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Leave Policy not found");
    }
}
