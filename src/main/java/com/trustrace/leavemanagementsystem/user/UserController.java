package com.trustrace.leavemanagementsystem.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private UserService us;

    @GetMapping("all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(us.getAllUsers());
    }

    @GetMapping
    public ResponseEntity<Page<UserDto>> getUsersOfPage(@RequestParam int page, @RequestParam int size){
        return ResponseEntity.ok(us.getUsersOfPage(page, size));
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserDto(@PathVariable("id") String id){
        UserDto user=us.getUserById(id);
        if(user!=null)
            return ResponseEntity.ok(user);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> createUser(@RequestBody User user){
        if(user!=null) {
            return ResponseEntity.ok(us.createUser(user));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> updateUser(@PathVariable String id, @RequestBody User user){
        if(user == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        UserDto userDto = us.updateUser(id, user);

        if(userDto == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        return ResponseEntity.ok(userDto);
    }

    @PutMapping("profile/{id}")
    public ResponseEntity<String> changeProfile(@PathVariable("id") String id, @RequestParam MultipartFile img) throws Exception {
        String response = us.changeProfile(id,img);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable("id") String id){
        boolean status=us.deleteUser(id);
        if(!status) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User id Not Found");
        return ResponseEntity.ok("Deleted Successfully!!");
    }

    @GetMapping("search")
    public ResponseEntity<List<ManagerDto>> searchUser(@RequestParam String name) {
        return ResponseEntity.ok(us.searchUserByName(name));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        if (us.requestPasswordReset(email)) {
            return ResponseEntity.ok("Reset link sent to email.");
        }
        return ResponseEntity.badRequest().body("User not found.");
    }

    @GetMapping("/validate-reset-token")
    public ResponseEntity<Map<String, Boolean>> validateToken(@RequestParam String token) {
        boolean isValid = us.validatePasswordResetToken(token);
        return ResponseEntity.ok(Collections.singletonMap("valid", isValid));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        return us.resetPassword(token, newPassword) ?
                ResponseEntity.ok("Password updated successfully.") :
                ResponseEntity.badRequest().body("Invalid or expired token.");
    }

}
