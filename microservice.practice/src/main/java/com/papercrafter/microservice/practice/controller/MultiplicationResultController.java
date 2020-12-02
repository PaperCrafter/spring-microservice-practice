package com.papercrafter.microservice.practice.controller;

import com.papercrafter.microservice.practice.domain.Multiplication;
import com.papercrafter.microservice.practice.domain.MultiplicationResultAttempt;
import com.papercrafter.microservice.practice.service.MultiplicationService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/results")
public class MultiplicationResultController {
    private final MultiplicationService multiplicationService;

    @Autowired
    public MultiplicationResultController(MultiplicationService multiplicationService){
        this.multiplicationService = multiplicationService;
    }

    @PostMapping
    public ResponseEntity<ResultResponse> postResult(@RequestBody MultiplicationResultAttempt
                                                      multiplicationResultAttempt){
        return ResponseEntity.ok(
                new ResultResponse(multiplicationService
                    .checkAttempt(multiplicationResultAttempt)));
    }

    @RequiredArgsConstructor
    @NoArgsConstructor(force = true)
    @Getter
    public static final class ResultResponse{
        private final boolean correct;
    }
}
