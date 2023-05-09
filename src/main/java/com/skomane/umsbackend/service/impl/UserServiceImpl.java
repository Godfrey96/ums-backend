package com.skomane.umsbackend.service.impl;

import com.skomane.umsbackend.exceptions.UserDoesNotExistException;
import com.skomane.umsbackend.jwt.JwtAuthenticationFilter;
import com.skomane.umsbackend.model.Role;
import com.skomane.umsbackend.model.User;
import com.skomane.umsbackend.repository.UserRepository;
import com.skomane.umsbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationFilter authenticationFilter;


    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<User>> getAllUsersOfRoleUser(Role role) {
        try {
            return new ResponseEntity<>(userRepository.findByRole(role), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try {
            var user = userRepository.findByEmail(requestMap.get("email"));
            if (!user.equals(null)) {
                user.get().setStatus(requestMap.get("status"));
                userRepository.save(user.get());
                return new ResponseEntity<>("Status updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to update status", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {
            var user = userRepository.findByEmail(authenticationFilter.getCurrentUser());
            if (!user.equals(null)) {
                if (passwordEncoder.matches(requestMap.get("oldPassword"), user.get().getPassword())) {
                    user.get().setPassword(passwordEncoder.encode(requestMap.get("newPassword")));
                    userRepository.save(user.get());
                    return new ResponseEntity<>("Password updated successfully", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Incorrect current password", HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteUser(Integer id) {
        try {
            Optional optional = userRepository.findById(id);
            if (!optional.isEmpty()) {
                userRepository.deleteById(id);
                return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User id does not exists", HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateUser(Map<String, String> requestMap) {
        try {
            var user = userRepository.findByEmail(authenticationFilter.getCurrentUser());
            if (!user.equals(null)) {
                user.get().setMyUsername(requestMap.get("myUsername"));
                user.get().setPhone(requestMap.get("phone"));
                user.get().setEmail(requestMap.get("email"));
                userRepository.save(user.get());
                return new ResponseEntity<>("Your details are updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public User updateUserByAdmin(User user, Integer id) {
        try {
            User existingUser = userRepository.findById(id)
                    .orElseThrow(() -> new UserDoesNotExistException());
            existingUser.setMyUsername(user.getMyUsername());
            existingUser.setPhone(user.getPhone());
            existingUser.setEmail(user.getEmail());
            existingUser.setStatus(user.getStatus());
            existingUser.setRole(user.getRole());

            userRepository.save(existingUser);

            return existingUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new User();
    }
}
