package com.papercrafter.microservice.practice.repository;

import com.papercrafter.microservice.practice.domain.MultiplicationResultAttempt;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MultiplicationResultAttemptRepository
        extends CrudRepository<MultiplicationResultAttempt, Long> {
    List<MultiplicationResultAttempt> findTop5ByUserAliasOrderByIdDesc(String userAlias);
}
