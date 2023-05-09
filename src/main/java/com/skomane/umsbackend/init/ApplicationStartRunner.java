package com.skomane.umsbackend.init;

import com.skomane.umsbackend.model.Role;
import com.skomane.umsbackend.model.User;
import com.skomane.umsbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationStartRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        User user = new User().builder()
                .myUsername("admin")
                .phone("0820888267")
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("123456789"))
                .role(Role.ADMIN)
                .status("true")
                .build();
        userRepository.save(user);
    }
}
