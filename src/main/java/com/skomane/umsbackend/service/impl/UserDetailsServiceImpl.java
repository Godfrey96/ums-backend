package com.skomane.umsbackend.service.impl;

import com.skomane.umsbackend.exceptions.UserDoesNotExistException;
import com.skomane.umsbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private com.skomane.umsbackend.model.User userDetail;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        userDetail = userRepository.findByEmail(username).get();
        if (!Objects.isNull(userDetail)) {
            return new User(userDetail.getEmail(), userDetail.getPassword(), new ArrayList<>());
        } else {
            throw new UserDoesNotExistException();
        }
    }

    public com.skomane.umsbackend.model.User getUserDetail() {
        return userDetail;
    }

    public Optional<org.springframework.security.core.userdetails.User> getCurrentUser(){
        org.springframework.security.core.userdetails.User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.of(principal);
    }
}
