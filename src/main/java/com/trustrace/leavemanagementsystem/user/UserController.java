package com.trustrace.leavemanagementsystem.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private UserService us;

    @GetMapping("all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(us.getAllUsers());
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<User>> getUsersOfPage(@RequestParam int page, @RequestParam int size){
        return ResponseEntity.ok(us.getUsersOfPage(page, size));
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") String id){
        User user=us.getUserById(id);
        if(user!=null)
            return ResponseEntity.ok(user);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> createUser(@RequestBody User user){
        if(user!=null) {
            return ResponseEntity.ok(us.createUser(user));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
//    @PutMapping("{id}")
////    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<User> updateUser(@PathVariable("id") String id,@RequestBody User user){
//        if(user==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        User p=us.updateUser(id,user);
//        if(p==null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        return ResponseEntity.ok(p);
//    }
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable("id") String id){
        boolean status=us.deleteUser(id);
        if(!status) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User id Not Found");
        return ResponseEntity.ok("Deleted Successfully!!");
    }

}
