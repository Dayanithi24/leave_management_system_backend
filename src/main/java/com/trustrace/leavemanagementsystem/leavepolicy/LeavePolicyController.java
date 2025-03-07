package com.trustrace.leavemanagementsystem.leavepolicy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/leave-policy/")
public class LeavePolicyController {
    @Autowired
    private LeavePolicyService lps;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HR')")
    public ResponseEntity<LeavePolicyDto> createLeavePolicy(@RequestBody LeavePolicy leavePolicy, @RequestParam String userId, @RequestParam String timezone) {
        if (leavePolicy == null || userId == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        LeavePolicyDto leavePolicyDto = lps.createLeavePolicy(leavePolicy, userId, timezone);
        return ResponseEntity.ok(leavePolicyDto);
    }

    @GetMapping
    public ResponseEntity<List<LeavePolicyDto>> getAllLeavePolicies(@RequestParam String timezone) {
        return ResponseEntity.ok(lps.getAllLeavePolicies(timezone));
    }

    @GetMapping("{id}")
    public ResponseEntity<LeavePolicyDto> getLeavePolicyById(@PathVariable String id, @RequestParam String timezone) {
        if(id == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        LeavePolicyDto leavePolicyDto = lps.getLeavePolicyById(id, timezone);
        if(leavePolicyDto == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.ok(leavePolicyDto);
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
