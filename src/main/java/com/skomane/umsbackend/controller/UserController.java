package com.skomane.umsbackend.controller;

import com.skomane.umsbackend.dto.*;
import com.skomane.umsbackend.exceptions.UnableToResolvePhotoException;
import com.skomane.umsbackend.jwt.JwtUtils;
import com.skomane.umsbackend.model.Role;
import com.skomane.umsbackend.model.User;
import com.skomane.umsbackend.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User")
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
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeDto passwordChangeDto) {
        try {
            return userService.changePassword(passwordChangeDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/update-status")
    public ResponseEntity<String> updateStatus(@RequestBody StatusDto statusDto) {
        try {
            return userService.updateStatus(statusDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/update-user")
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserDto userDto) {
        try {
            return userService.updateUser(userDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/update-user-details")
    public ResponseEntity<User> updateUserDetailsByAdmin(@RequestBody UpdateUserAdminDto updateUserAdminDto) {
        return new ResponseEntity<>(userService.updateUserDetailsByAdmin(updateUserAdminDto), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/upload-image", consumes = {"multipart/form-data"})
    public User uploadProfilePicture(@RequestParam("image") MultipartFile file) throws Exception {
        return userService.setProfileOrBannerPicture(file, "pfp");
    }

    @GetMapping("/total-admins")
    public Integer getAllAdminsCount() {
        return userService.getAllAdminsCountByAdminRole();
    }

    @GetMapping("/total-users")
    public Integer getAllUsersCount() {
        return userService.getAllUsersCountByUserRole();
    }

    @GetMapping("/total-active")
    public Integer getAllUsersStatusTrue() {
        return userService.getAllUsersCountByStatusTrue();
    }

    @GetMapping("/total-disable")
    public Integer getAllUsersStatusFalse() {
        return userService.getAllUsersCountByStatusFalse();
    }


}
