package com.papercrafter.microservice.practice.service;

import com.papercrafter.microservice.practice.domain.Multiplication;
import com.papercrafter.microservice.practice.domain.User;
import com.papercrafter.microservice.practice.service.impl.MultiplicationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class MultiplicationServiceTest {
    @Mock
    private RandomGeneratorService randomGeneratorService;

    @Autowired
    private MultiplicationService multiplicationService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        multiplicationService = new MultiplicationServiceImpl(randomGeneratorService);
    }

    @Test
    public void createRandomMultiplicationTest(){
        //given
        given(randomGeneratorService.generateRandomFactor()).willReturn(50,30);
        //when
        Multiplication multiplication = multiplicationService.createMultiplication();
        //assert
        assertThat(multiplication.getFactorA()).isEqualTo(50);
        assertThat(multiplication.getFactorB()).isEqualTo(30);
    }
}