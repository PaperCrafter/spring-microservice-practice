package com.papercrafter.microservice.practice.domain;

public class Multiplication {
    private int factorA;
    private int factorB;
    private int result;

    public Multiplication(int factorA, int factorB){
        this.factorA = factorA;
        this.factorB = factorB;
        this.result = factorA * factorB;
    }

    public int getFactorA(){
        return this.factorA;
    }

    public int getFactorB(){
        return this.factorB;
    }

    public int getResult(){
        return this.getResult();
    }

    @Override
    public String toString(){
        return "Multiplication{" +
                "factorA=" + factorA +
                ", factorB=" + factorB +
                ", result=" + result + "}";
    }
}
