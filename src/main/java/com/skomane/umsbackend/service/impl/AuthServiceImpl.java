package com.skomane.umsbackend.service.impl;

import com.skomane.umsbackend.config.ApplicationConfig;
import com.skomane.umsbackend.dto.AuthenticationRequest;
import com.skomane.umsbackend.dto.AuthenticationResponse;
import com.skomane.umsbackend.dto.RegisterRequest;
import com.skomane.umsbackend.exceptions.*;
import com.skomane.umsbackend.jwt.JwtAuthenticationFilter;
import com.skomane.umsbackend.jwt.JwtUtils;
import com.skomane.umsbackend.model.Role;
import com.skomane.umsbackend.model.User;
import com.skomane.umsbackend.repository.UserRepository;
import com.skomane.umsbackend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthenticationFilter authenticationFilter;


    @Override
    public User register(RegisterRequest request) {
        User user = new User().builder()
                .myUsername(request.getMyUsername())
                .phone(request.getPhone())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .status("true")
                .build();

        try {
            return userRepository.save(user);
        } catch (Exception e){
            throw new EmailAlreadyTakenException();
        }
    }

    @Override
    public AuthenticationResponse login(Map<String, String> requestMap) {
        String email = requestMap.get("email");
        String password = requestMap.get("password");
        try {
            Authentication auth =  authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            if (auth.isAuthenticated()){
                if (userDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")){
                    User user = getUserByEmail(email);
                    String jwtToken = jwtUtils.generateToken(email,user.getRole().name());
                    return new AuthenticationResponse(user, jwtToken);
                } else {
                    throw new DisabledAccountException();
//                    return new AuthenticationResponse(null, "Your Account is Disabled. Wait for an Admin to activate");
                }
            }

        } catch (DisabledException e) {
//            e.printStackTrace();
            throw new ErrorResponseException();
        } catch (BadCredentialsException e){
            throw new InvalidCredentialsException();
        }
        return new AuthenticationResponse(null, "Bad Credentials");
    }

    @Override
    public User getUserByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserDoesNotExistException());
    }


}
