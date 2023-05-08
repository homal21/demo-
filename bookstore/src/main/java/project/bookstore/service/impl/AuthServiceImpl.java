package project.bookstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.bookstore.entity.Roles;
import project.bookstore.entity.User;
import project.bookstore.exception.AppApiException;
import project.bookstore.repo.RolesRepo;
import project.bookstore.repo.UserRepo;
import project.bookstore.request.LoginRequest;
import project.bookstore.request.SignUpRequest;
import project.bookstore.security.jwt.JwtTokenProvider;
import project.bookstore.service.AuthService;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    RolesRepo rolesRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Override
    public String login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepo.findByUsername(loginRequest.getUsername());
        String jwt = tokenProvider.generateToken(user);
        return jwt;
    }

    @Override
    public String signUp(SignUpRequest signUpRequest) {
        if (userRepo.existsByUsername(signUpRequest.getUsername())) {
            throw new AppApiException(HttpStatus.BAD_REQUEST, "Ten dang nhap da ton tai");
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
        return "Dang ky thanh cong";
    }
}
