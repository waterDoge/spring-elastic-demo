package com.ata.elastic.service;

import com.ata.elastic.config.ApplicationProperties;
import com.ata.elastic.entity.User;
import com.ata.elastic.entity.UserProfile;
import com.ata.elastic.entity.dto.UserDTO;
import com.ata.elastic.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    private UserRepository userRepository;
    private ApplicationProperties properties;

    public UserService(UserRepository userRepository, ApplicationProperties properties) {
        this.userRepository = userRepository;
        this.properties = properties;
    }

    public Mono<User> create(UserDTO user) {
        return create(user.getUsername(), user.getNickname(), user.getPassword().getBytes(), user.getProfile());
    }

    @Transactional
    public Mono<User> create(String username, String nickname, byte[] rawPassword, UserProfile profile) {
        if (userRepository.countByUsername(username) != 0) {
            return Mono.empty();
        }

        User user = new User();
        user.setUsername(username);
        user.setNickname(nickname);
        user.setStrategy(properties.getPasswordStrategy());
        user.setProfile(profile);

        final byte[] salt = String.valueOf(System.currentTimeMillis()).getBytes();
        user.setSalt(salt);
        final String password = properties.getPasswordStrategy().generatePassword(rawPassword, salt);
        user.setPassword(password);
        return Mono.justOrEmpty(userRepository.save(user));
    }
}
