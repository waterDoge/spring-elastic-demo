package com.ata.elastic.repository;

import com.ata.elastic.entity.UserProfile;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserProfileRepository extends PagingAndSortingRepository<UserProfile, String> {
}
