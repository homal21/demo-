package project.bookstore.service;

import project.bookstore.request.LoginRequest;
import project.bookstore.request.SignUpRequest;

public interface AuthService {
    String login(LoginRequest loginRequest);
    String signUp(SignUpRequest signUpRequest);
}
