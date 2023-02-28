package com.example.demoWebBanHang.controller;

import com.example.demoWebBanHang.Request.LoginRequest;
import com.example.demoWebBanHang.Request.SignUpRequest;
import com.example.demoWebBanHang.Entity.Roles;
import com.example.demoWebBanHang.Entity.User;
import com.example.demoWebBanHang.Repo.RolesRepo;
import com.example.demoWebBanHang.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
public class AuthController {
    @Autowired
    RolesRepo rolesRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
           SecurityContextHolder.getContext().setAuthentication(authentication);
           User user = userRepo.findByUsername(loginRequest.getUsername());
            return ResponseEntity.ok().body(loginRequest);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpRequest signUpRequest) {
        if (userRepo.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>("tai khoan trung", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setName(signUpRequest.getName());
        Roles roles = rolesRepo.findByName("ROLE_USER");
        Set<Roles> role = new HashSet<>();
        role.add(roles);
        user.setRoles(role);
        userRepo.save(user);
        return ResponseEntity.ok("dang ky thanh cong");
    }

}