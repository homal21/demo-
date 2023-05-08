package project.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.bookstore.exception.AppApiException;
import project.bookstore.request.LoginRequest;
import project.bookstore.request.SignUpRequest;
import project.bookstore.service.AuthService;

@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            return ResponseEntity.ok().body(authService.login(loginRequest));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequest signUpRequest) {
        try {
            return new ResponseEntity<>(authService.signUp(signUpRequest), HttpStatus.CREATED);
        } catch (AppApiException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

}


