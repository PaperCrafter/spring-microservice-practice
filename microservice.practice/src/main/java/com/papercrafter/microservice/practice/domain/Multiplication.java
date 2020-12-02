package com.papercrafter.microservice.practice.domain;

import lombok.*;

@Getter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
public final class Multiplication {
    private final int factorA;
    private final int factorB;

    Multiplication(){
        this(0, 0);
    }
}
