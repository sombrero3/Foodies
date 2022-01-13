package com.example.foodies.model;

public class IdGenerator {
    Integer nextId;

    public static final IdGenerator instance = new IdGenerator();

    public IdGenerator(){
        nextId =0;
    }

    public Integer getNextId() {
        nextId+=1;
        return nextId;
    }
}
