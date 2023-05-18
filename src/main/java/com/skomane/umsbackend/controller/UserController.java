package com.skomane.umsbackend.controller;

import com.skomane.umsbackend.exceptions.UnableToResolvePhotoException;
import com.skomane.umsbackend.jwt.JwtUtils;
import com.skomane.umsbackend.model.Role;
import com.skomane.umsbackend.model.User;
import com.skomane.umsbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin("*")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtils jwtUtils;

    @GetMapping("/get-all-users")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            return userService.getAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/get-user")
    public ResponseEntity<User> getSingleUser() {
        return new ResponseEntity<>(userService.getSingleUser(), HttpStatus.OK);
    }

    @GetMapping("/get-users-only")
    public ResponseEntity<List<User>> getUsersOnly() {
        try {
            return userService.getAllUsersOfRoleUser(Role.USER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        try {
            return userService.deleteUser(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> requestMap) {
        try {
            return userService.changePassword(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/update-status")
    public ResponseEntity<String> updateStatus(@RequestBody Map<String, String> requestMap) {
        try {
            return userService.updateStatus(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/update-user")
    public ResponseEntity<String> updateUser(@RequestBody Map<String, String> requestMap) {
        try {
            return userService.updateUser(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/update-user-details")
    public ResponseEntity<User> updateUserDetailsByAdmin(@RequestBody User user) {
        return new ResponseEntity<>(userService.updateUserDetailsByAdmin(user), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping("/pfp")
    public User uploadProfilePicture(@RequestParam("image") MultipartFile file) throws Exception {
        return userService.setProfileOrBannerPicture(file, "pfp");
    }

    @PostMapping("/banner")
    public User uploadBannerPicture(@RequestParam("image")MultipartFile file) throws Exception {
        return userService.setProfileOrBannerPicture(file, "bnr");
    }


}
