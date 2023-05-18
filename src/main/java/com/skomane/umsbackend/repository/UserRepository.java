package com.skomane.umsbackend.repository;

import com.skomane.umsbackend.model.Role;
import com.skomane.umsbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);

    @Query("SELECT COUNT(u) FROM User u WHERE u.role='ADMIN'")
    Integer totalAdminByAdminRole();

    @Query("SELECT COUNT(u) FROM User u WHERE u.role='USER'")
    Integer totalUsersByUserRole();

    @Query("SELECT COUNT(u) FROM User u WHERE u.status='true'")
    Integer totalUsersByStatusTrue();

    @Query("SELECT COUNT(u) FROM User u WHERE u.status='false'")
    Integer totalUsersByStatusFalse();

}
