package com.papercrafter.microservice.practice.service;

import com.papercrafter.microservice.practice.domain.Multiplication;
import com.papercrafter.microservice.practice.domain.MultiplicationResultAttempt;

public interface MultiplicationService {
    Multiplication createMultiplication();

    boolean checkAttempt(final MultiplicationResultAttempt resultAttempt);
}
