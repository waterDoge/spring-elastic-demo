package com.ata.elastic.repository;

import com.ata.elastic.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, String> {

    int countByUsername(String username);

    Optional<User> findByUsername(String username);

    //通过这种方式禁用HTTP DELETE
    @Override
    @RestResource(exported = false)
    void delete(User entity);

    @Override
    @RestResource(exported = false)
    void deleteById(String s);
}
