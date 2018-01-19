package com.github.invictum.mei.condition;

/**
 * Abstraction describes different command condition
 */
public interface Condition {
    /**
     * Verifies condition for application
     *
     * @return true is condition is applicable
     */
    boolean check();

    /**
     * Verifies passed condition expression validity
     *
     * @return true is condition is valid and could be used, false otherwise
     */
    boolean isValid();
}
