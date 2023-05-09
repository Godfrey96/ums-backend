package com.skomane.umsbackend.repository;

import com.skomane.umsbackend.model.Role;
import com.skomane.umsbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);

}
