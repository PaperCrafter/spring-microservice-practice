package com.papercrafter.microservice.practice.service.impl;

import com.papercrafter.microservice.practice.domain.Multiplication;
import com.papercrafter.microservice.practice.domain.MultiplicationResultAttempt;
import com.papercrafter.microservice.practice.domain.User;
import com.papercrafter.microservice.practice.event.EventDispatcher;
import com.papercrafter.microservice.practice.repository.MultiplicationRepository;
import com.papercrafter.microservice.practice.repository.MultiplicationResultAttemptRepository;
import com.papercrafter.microservice.practice.repository.UserRepository;
import com.papercrafter.microservice.practice.service.RandomGeneratorService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class MultiplicationServiceImplTest {
    private MultiplicationServiceImpl multiplicationServiceImpl;

    @Mock
    private RandomGeneratorService randomGeneratorService;

    @Mock
    private MultiplicationResultAttemptRepository attemptRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MultiplicationRepository multiplicationRepository;

    @Mock
    private EventDispatcher eventDispatcher;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        multiplicationServiceImpl = new MultiplicationServiceImpl(randomGeneratorService,
                attemptRepository, userRepository, multiplicationRepository, eventDispatcher);
    }

    @Test
    public void createRandomMultiplicationTest() {
        //given
        given(randomGeneratorService.generateRandomFactor()).willReturn(50,30);
        //when
        Multiplication multiplication = multiplicationServiceImpl.createMultiplication();
        //assert
        assertThat(multiplication.getFactorA()).isEqualTo(50);
        assertThat(multiplication.getFactorB()).isEqualTo(30);
    }

    @Test
    public void checkCorrectAttemptTest() {
        //given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("John_doe");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(
                user, multiplication, 3000, false);
        MultiplicationResultAttempt verifiedAttempt = new MultiplicationResultAttempt(
                user, multiplication, 3000, true);
        ///when
        boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);
        //assert
        assertThat(attemptResult).isTrue();
        verify(attemptRepository).save(verifiedAttempt);
    }

    @Test
    public void checkWrongAttemptTest() {
        //given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("John_doe");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(
                user, multiplication, 3010, false);
        given(userRepository.findByAlias("John_doe")).willReturn(Optional.empty());
        ///when
        boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);
        //assert
        assertThat(attemptResult).isFalse();
        verify(attemptRepository).save(attempt);
    }

    @Test
    public void retrieveStatsTest() {
        //given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("John_doe");
        MultiplicationResultAttempt attempt1 =
                new MultiplicationResultAttempt(user , multiplication, 3010, false);
        MultiplicationResultAttempt attempt2 =
                new MultiplicationResultAttempt(user , multiplication, 3051, false);
        List<MultiplicationResultAttempt> latestAttempts = Lists.newArrayList(attempt1, attempt2);
        given(userRepository.findByAlias("john_doe")).willReturn(Optional.empty());
        given(attemptRepository.findTop5ByUserAliasOrderByIdDesc("john_doe"))
                .willReturn(latestAttempts);
        //when
        List<MultiplicationResultAttempt> latestAttemptsResult =
                multiplicationServiceImpl.getStatsForUser("john_doe");
        //then
        assertThat(latestAttemptsResult).isEqualTo(latestAttempts);
    }
}