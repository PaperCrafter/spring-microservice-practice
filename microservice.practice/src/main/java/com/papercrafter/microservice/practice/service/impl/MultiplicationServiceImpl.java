package com.papercrafter.microservice.practice.service.impl;

import com.papercrafter.microservice.practice.domain.Multiplication;
import com.papercrafter.microservice.practice.domain.MultiplicationResultAttempt;
import com.papercrafter.microservice.practice.service.MultiplicationService;
import com.papercrafter.microservice.practice.service.RandomGeneratorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
    public boolean checkAttempt(MultiplicationResultAttempt attempt) {
        boolean correct = attempt.getResultAttempt() ==
                attempt.getMultiplication().getFactorA() *
                        attempt.getMultiplication().getFactorB();
        Assert.isTrue(!attempt.isCorrect(), "채점한 상태로 보낼 수 없습니다!");
        MultiplicationResultAttempt checkedAttempt = new MultiplicationResultAttempt(attempt.getUser(),
                attempt.getMultiplication(), attempt.getResultAttempt(), correct);
        return correct;
    }
}
