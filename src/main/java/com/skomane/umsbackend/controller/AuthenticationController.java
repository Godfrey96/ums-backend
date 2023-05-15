package com.skomane.umsbackend.controller;

import com.skomane.umsbackend.dto.AuthenticationResponse;
import com.skomane.umsbackend.dto.RegisterRequest;
import com.skomane.umsbackend.exceptions.EmailAlreadyTakenException;
import com.skomane.umsbackend.exceptions.InvalidCredentialsException;
import com.skomane.umsbackend.exceptions.UsernameAlreadyTakenException;
import com.skomane.umsbackend.model.User;
import com.skomane.umsbackend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService authService;

    @ExceptionHandler({EmailAlreadyTakenException.class})
    public ResponseEntity<String> handleEmailTaken() {
        return new ResponseEntity<>("The email you provided is already in use", HttpStatus.CONFLICT);
    }

    @ExceptionHandler({UsernameAlreadyTakenException.class})
    public ResponseEntity<String> handleUsernameTaken() {
        return new ResponseEntity<>("The username you provided is already in use", HttpStatus.CONFLICT);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }

    @ExceptionHandler({ErrorResponseException.class})
    public ResponseEntity<String> handleDisableAccount() {
        return new ResponseEntity<>("Your Account is Disabled. Wait for an Admin to activate", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidCredentialsException.class})
    public ResponseEntity<String> handleInvalidCredentials() {
        return new ResponseEntity<>("Invalid email or password entered", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody Map<String, String> requestMap) {
        return new ResponseEntity<>(authService.login(requestMap), HttpStatus.OK);
    }


}
