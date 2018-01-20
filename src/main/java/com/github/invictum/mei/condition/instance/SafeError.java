package com.github.invictum.mei.condition.instance;

/**
 * Stub condition used to remove task with invalid conditions
 */
public class SafeError extends AbstractCondition {

    @Override
    public boolean check() {
        return true;
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
