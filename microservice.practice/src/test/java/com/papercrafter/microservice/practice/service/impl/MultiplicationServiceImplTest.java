package com.papercrafter.microservice.practice.service.impl;

import com.papercrafter.microservice.practice.domain.Multiplication;
import com.papercrafter.microservice.practice.domain.MultiplicationResultAttempt;
import com.papercrafter.microservice.practice.domain.User;
import com.papercrafter.microservice.practice.service.RandomGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class MultiplicationServiceImplTest {
    private MultiplicationServiceImpl multiplicationServiceImpl;

    @Mock
    private RandomGeneratorService randomGeneratorService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        multiplicationServiceImpl = new MultiplicationServiceImpl(randomGeneratorService);
    }

    @Test
    public void createRandomMultiplicationTest(){
        //given
        given(randomGeneratorService.generateRandomFactor()).willReturn(50,30);
        //when
        Multiplication multiplication = multiplicationServiceImpl.createMultiplication();
        //assert
        assertThat(multiplication.getFactorA()).isEqualTo(50);
        assertThat(multiplication.getFactorB()).isEqualTo(30);
    }

    @Test
    public void checkCorrectAttemptTest(){
        //given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("John_doe");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3000);
        ///when
        boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);
        //assert
        assertThat(attemptResult).isTrue();
    }

    @Test
    public void checkWrongAttemptTest(){
        //given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("John_doe");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3010);
        ///when
        boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);
        //assert
        assertThat(attemptResult).isFalse();
    }
}