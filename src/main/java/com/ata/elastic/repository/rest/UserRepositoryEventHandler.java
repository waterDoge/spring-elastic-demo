package com.ata.elastic.repository.rest;

import com.ata.elastic.entity.User;
import com.ata.elastic.repository.UserRepository;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class UserRepositoryEventHandler {
    private UserRepository userRepository;

    public UserRepositoryEventHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @HandleBeforeSave
    public void beforeSave(User user) {
        System.out.println(user);
    }
}
