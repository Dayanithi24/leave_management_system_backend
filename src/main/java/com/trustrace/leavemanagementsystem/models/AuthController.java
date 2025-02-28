package com.trustrace.leavemanagementsystem.models;

import com.trustrace.leavemanagementsystem.jwt.JwtUtil;
import com.trustrace.leavemanagementsystem.security.MyUserDetailsService;
import com.trustrace.leavemanagementsystem.user.User;
import com.trustrace.leavemanagementsystem.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = {"http://localhost:4200"})
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private UserService userService;

    @PostMapping("authenticate/")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("msg", "Incorrect Email or Password"));
        }

        UserDetails userDetails = myUserDetailsService.loadUserByUsername(authRequest.getEmail());
        String jwt = jwtUtil.generateToken(userDetails);
        User user = userService.getUserByEmail((authRequest.getEmail()));

        return ResponseEntity.ok(new AuthenticationResponse(jwt, user.getId()));
    }

}
