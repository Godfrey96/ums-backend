package com.skomane.umsbackend.service;

import com.skomane.umsbackend.dto.AuthenticationRequest;
import com.skomane.umsbackend.dto.AuthenticationResponse;
import com.skomane.umsbackend.dto.RegisterRequest;
import com.skomane.umsbackend.model.User;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthService {

    User register(RegisterRequest request);
    AuthenticationResponse login(AuthenticationRequest request);
    User getUserByEmail(String username);
}
