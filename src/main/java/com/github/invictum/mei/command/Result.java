package com.github.invictum.mei.command;

/**
 * Describes command execution results
 */
public enum Result {
    /**
     * Command executed and all condition are meet
     */
    ALL_OK,
    /**
     * Conditions are valid, but do not meet
     */
    CONDITION_FAIL,
    /**
     * Conditions are invalid
     */
    CONDITION_ERROR
}
