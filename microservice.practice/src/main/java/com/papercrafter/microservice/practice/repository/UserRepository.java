package com.papercrafter.microservice.practice.repository;

import com.papercrafter.microservice.practice.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByAlias(final String alias);
}
