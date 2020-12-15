package com.papercrafter.microservice.practice.repository;

import com.papercrafter.microservice.practice.domain.Multiplication;
import org.springframework.data.repository.CrudRepository;

public interface MultiplicationRepository extends CrudRepository<Multiplication, Long> {
}
