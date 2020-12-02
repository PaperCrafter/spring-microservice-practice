package com.papercrafter.microservice.practice.service.impl;

import com.papercrafter.microservice.practice.domain.Multiplication;
import com.papercrafter.microservice.practice.domain.MultiplicationResultAttempt;
import com.papercrafter.microservice.practice.service.MultiplicationService;
import com.papercrafter.microservice.practice.service.RandomGeneratorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MultiplicationServiceImpl implements MultiplicationService {
    private RandomGeneratorService randomGeneratorService;

    @Override
    public Multiplication createMultiplication() {
        int factorA = randomGeneratorService.generateRandomFactor();
        int factorB = randomGeneratorService.generateRandomFactor();
        return new Multiplication(factorA, factorB);
    }

    @Override
    public boolean checkAttempt(MultiplicationResultAttempt resultAttempt) {
        return resultAttempt.getResultAttempt() ==
                resultAttempt.getMultiplication().getFactorA() *
                resultAttempt.getMultiplication().getFactorB();
    }
}
