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

        User user1 = new User().builder()
                .myUsername("mogau")
                .phone("0720461090")
                .email("mogau@gmail.com")
                .password(passwordEncoder.encode("123456789"))
                .role(Role.USER)
                .status("true")
                .build();
        userRepository.save(user1);

        User user2 = new User().builder()
                .myUsername("skomane")
                .phone("0720461090")
                .email("skomane@gmail.com")
                .password(passwordEncoder.encode("123456789"))
                .role(Role.USER)
                .status("false")
                .build();
        userRepository.save(user2);

        User user3 = new User().builder()
                .myUsername("leman")
                .phone("0720461090")
                .email("leman@gmail.com")
                .password(passwordEncoder.encode("123456789"))
                .role(Role.ADMIN)
                .status("false")
                .build();
        userRepository.save(user3);

        User user4 = new User().builder()
                .myUsername("godfrey")
                .phone("0720461090")
                .email("godfrey@gmail.com")
                .password(passwordEncoder.encode("123456789"))
                .role(Role.USER)
                .status("false")
                .build();
        userRepository.save(user4);

        User user5 = new User().builder()
                .myUsername("mike")
                .phone("0820888267")
                .email("mike@gmail.com")
                .password(passwordEncoder.encode("123456789"))
                .role(Role.USER)
                .status("true")
                .build();
        userRepository.save(user5);
    }
}
