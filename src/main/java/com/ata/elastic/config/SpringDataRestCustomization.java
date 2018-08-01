package com.ata.elastic.config;

import com.ata.elastic.entity.User;
import com.ata.elastic.repository.UserRepository;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

//@Configuration
public class SpringDataRestCustomization extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {

        config.withEntityLookup().
                forRepository(UserRepository.class, User::getUsername, UserRepository::findByUsername);
    }

//    @Bean
//    public ResourceProcessor<Resource<User>> userProcessor() {
//        return resource -> {
////                resource.add(linkTo(methodOn(UserController.class).create(new UserDTO())).withRel("add"));
//            return resource;
//        };
//    }
}