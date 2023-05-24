package com.skomane.umsbackend.service;

import com.skomane.umsbackend.dto.*;
import com.skomane.umsbackend.model.Role;
import com.skomane.umsbackend.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface UserService {

    ResponseEntity<List<User>> getAllUsers();
    ResponseEntity<List<User>> getAllUsersOfRoleUser(Role role);
    User getSingleUser();
    ResponseEntity<String> updateStatus(StatusDto statusDto);
    ResponseEntity<String> changePassword(PasswordChangeDto passwordChangeDto);
    ResponseEntity<String> deleteUser(Integer id);
    ResponseEntity<String> updateUser(UpdateUserDto userDto);
    User updateUserDetailsByAdmin(UpdateUserAdminDto updateUserAdminDto);
    User getUserById(Integer id);
    User getUsernameByEmail(String username);
    User setProfileOrBannerPicture(MultipartFile file, String prefix);
    Integer getAllAdminsCountByAdminRole();
    Integer getAllUsersCountByUserRole();
    Integer getAllUsersCountByStatusTrue();
    Integer getAllUsersCountByStatusFalse();


}
