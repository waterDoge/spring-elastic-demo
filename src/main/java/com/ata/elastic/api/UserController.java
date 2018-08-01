package com.ata.elastic.api;

import com.ata.elastic.entity.User;
import com.ata.elastic.entity.dto.UserDTO;
import com.ata.elastic.service.UserService;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

import java.net.URI;

@RepositoryRestController
@RequestMapping("/users")
@Validated
public class UserController {

    private UserService userService;
    private RepositoryRestConfiguration configuration;

    public UserController(UserService userService, RepositoryRestConfiguration configuration) {
        this.userService = userService;
        this.configuration = configuration;
    }

    //路径应当是排除了${spring.data.rest.base-path}前缀的剩余部分
    @PostMapping
    public Mono<ResponseEntity<?>> create(@RequestBody UserDTO user) {
        return userService.create(user)
                .<ResponseEntity<?>>map(user1 -> {
                    final String self = configuration.getBasePath() + "/users/" + user1.getId();
                    final Resource<User> resource = new Resource<>(user1);
                    resource.add(new Link(self));
                    return ResponseEntity.created(URI.create(self)).body(resource);
                })
                .defaultIfEmpty(ResponseEntity.badRequest().body("用户已存在"));
    }
}
