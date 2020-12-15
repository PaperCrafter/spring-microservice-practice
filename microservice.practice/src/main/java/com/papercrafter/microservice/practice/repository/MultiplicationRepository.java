package com.papercrafter.microservice.practice.repository;

import com.papercrafter.microservice.practice.domain.Multiplication;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MultiplicationRepository extends CrudRepository<Multiplication, Long> {
    Optional<Multiplication> findMultiplicationByFactorAAndAndFactorB(int factorA, int factorB);
}
