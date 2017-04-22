package com.github.invictum.mei.conditions.instance;

public interface Condition {
    boolean checkCondition(String expression) throws IllegalArgumentException;
}
