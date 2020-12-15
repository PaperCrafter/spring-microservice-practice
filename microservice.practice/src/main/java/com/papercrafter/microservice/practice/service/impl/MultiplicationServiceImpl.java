package com.papercrafter.microservice.practice.service.impl;

import com.papercrafter.microservice.practice.domain.Multiplication;
import com.papercrafter.microservice.practice.domain.MultiplicationResultAttempt;
import com.papercrafter.microservice.practice.domain.User;
import com.papercrafter.microservice.practice.repository.MultiplicationRepository;
import com.papercrafter.microservice.practice.repository.MultiplicationResultAttemptRepository;
import com.papercrafter.microservice.practice.repository.UserRepository;
import com.papercrafter.microservice.practice.service.MultiplicationService;
import com.papercrafter.microservice.practice.service.RandomGeneratorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MultiplicationServiceImpl implements MultiplicationService {
    private final RandomGeneratorService randomGeneratorService;
    private final MultiplicationResultAttemptRepository attemptRepository;
    private final UserRepository userRepository;
    private final MultiplicationRepository multiplicationRepository;

    @Override
    public Multiplication createMultiplication() {
        int factorA = randomGeneratorService.generateRandomFactor();
        int factorB = randomGeneratorService.generateRandomFactor();
        return new Multiplication(factorA, factorB);
    }

    @Transactional
    @Override
    public boolean checkAttempt(MultiplicationResultAttempt attempt) {
        Optional<User> user = userRepository.findByAlias(attempt.getUser().getAlias());
        Assert.isTrue(!attempt.isCorrect(), "채점한 상태로 보낼 수 없습니다!");

        Optional<Multiplication> multiplication = multiplicationRepository
                .findMultiplicationByFactorAAndAndFactorB(
                        attempt.getMultiplication().getFactorA(),
                        attempt.getMultiplication().getFactorB());

        boolean isCorrect = attempt.getResultAttempt() ==
                attempt.getMultiplication().getFactorA() *
                        attempt.getMultiplication().getFactorB();

        MultiplicationResultAttempt checkedAttempt =
                new MultiplicationResultAttempt(
                        user.orElse(attempt.getUser()),
                        multiplication.orElse(attempt.getMultiplication()),
                        attempt.getResultAttempt(),
                        isCorrect);
        attemptRepository.save(checkedAttempt);

        return isCorrect;
    }

    @Override
    public List<MultiplicationResultAttempt> getStatsForUser(String userAlias){
        return attemptRepository.findTop5ByUserAliasOrderByIdDesc(userAlias);
    }
}
