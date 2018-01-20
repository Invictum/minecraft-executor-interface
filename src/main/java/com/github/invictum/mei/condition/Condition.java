package com.github.invictum.mei.condition;

/**
 * Abstraction describes different command condition
 */
public interface Condition {
    /**
     * Verifies is condition meets current criteria
     *
     * @return true is meet
     */
    boolean check();

    /**
     * Verifies passed condition expression validity
     *
     * @return true is condition is valid and could be used, false otherwise
     */
    boolean isValid();
}
