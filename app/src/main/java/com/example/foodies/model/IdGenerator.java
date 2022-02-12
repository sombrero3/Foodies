package com.example.foodies.model;

public class IdGenerator {
    int nextId;

    public static final IdGenerator instance = new IdGenerator();

    private IdGenerator(){
        nextId =0;
    }

    public int getNextId() {
        return nextId++;
    }
}
