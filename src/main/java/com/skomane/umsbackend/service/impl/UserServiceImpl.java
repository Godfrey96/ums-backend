package com.skomane.umsbackend.service.impl;

import com.skomane.umsbackend.exceptions.UnableToResolvePhotoException;
import com.skomane.umsbackend.exceptions.UserDoesNotExistException;
import com.skomane.umsbackend.jwt.JwtAuthenticationFilter;
import com.skomane.umsbackend.model.Image;
import com.skomane.umsbackend.model.Role;
import com.skomane.umsbackend.model.User;
import com.skomane.umsbackend.repository.UserRepository;
import com.skomane.umsbackend.service.ImageService;
import com.skomane.umsbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private final ImageService imageService;


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
    public User getSingleUser() {
        try {
            if (authenticationFilter.getCurrentUser() != null){
                var user = userRepository.findByEmail(authenticationFilter.getCurrentUser());
                if (user!= null) {
                    user.get().getEmail();
                    user.get().getPhone();
                    user.get().getMyUsername();
                    return user.get();
                } else {
                    throw new UserDoesNotExistException();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new UserDoesNotExistException();
    }


    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try {
            Optional<User> optional = userRepository.findById(Integer.parseInt(requestMap.get("id")));
            if (!optional.isEmpty()) {
                optional.get().setStatus(requestMap.get("status"));
                userRepository.save(optional.get());
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
    public User updateUserDetailsByAdmin(User user) {
        try {
            User existingUser = userRepository.findByEmail(user.getEmail())
                    .orElseThrow(() -> new UserDoesNotExistException());
            existingUser.setMyUsername(user.getMyUsername());
            existingUser.setPhone(user.getPhone());
            existingUser.setEmail(user.getEmail());
            existingUser.setRole(user.getRole());

            userRepository.save(existingUser);

            return existingUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new User();
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserDoesNotExistException());
    }

    @Override
    public User getUsernameByEmail(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UserDoesNotExistException());
    }

    @Override
    public User setProfileOrBannerPicture(MultipartFile file, String prefix) throws UnableToResolvePhotoException {
        var user = userRepository.findByEmail(authenticationFilter.getCurrentUser());

        System.out.println("user-profile: " + user);

        Image photo = imageService.uploadImage(file, prefix);

        try {
            if (prefix.equals("pfp")) {
                if (user.get().getProfilePicture() != null && !user.get().getProfilePicture().getImageName().equals("defaultpfp.png")) {
                    Path p = Paths.get(user.get().getProfilePicture().getImagePath());
                    Files.deleteIfExists(p);
                }
                user.get().setProfilePicture(photo);
            }
        } catch (Exception e){
            throw new UnableToResolvePhotoException();
        }

        return userRepository.save(user.get());
    }

    @Override
    public Integer getAllAdminsCountByAdminRole() {
        return userRepository.totalAdminByAdminRole();
    }

    @Override
    public Integer getAllUsersCountByUserRole() {
        return userRepository.totalUsersByUserRole();
    }

    @Override
    public Integer getAllUsersCountByStatusTrue() {
        return userRepository.totalUsersByStatusTrue();
    }

    @Override
    public Integer getAllUsersCountByStatusFalse() {
        return userRepository.totalUsersByStatusFalse();
    }
}
