package com.skomane.umsbackend.service;

import com.skomane.umsbackend.model.Role;
import com.skomane.umsbackend.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {

    ResponseEntity<List<User>> getAllUsers();
    ResponseEntity<List<User>> getAllUsersOfRoleUser(Role role);
    User getSingleUser();
    ResponseEntity<String> updateStatus(Map<String, String> requestMap);
    ResponseEntity<String> changePassword(Map<String, String> requestMap);
    ResponseEntity<String> deleteUser(Integer id);
    ResponseEntity<String> updateUser(Map<String, String> requestMap);
    User updateUserDetailsByAdmin(User user);
    User getUserById(Integer id);

}
