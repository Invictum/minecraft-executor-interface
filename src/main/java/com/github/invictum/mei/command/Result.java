package com.github.invictum.mei.command;

/**
 * Describes command execution results
 */
public enum Result {
    /**
     * Command executed and condition was meet
     */
    ALL_OK,
    /**
     * Conditions is valid, but does not meet
     */
    CONDITION_FAIL,
    /**
     * Condition was invalid
     */
    CONDITION_ERROR
}
