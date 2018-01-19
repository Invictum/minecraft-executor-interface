package com.github.invictum.mei.condition.instance;

import com.github.invictum.mei.condition.Condition;

public abstract class AbstractCondition implements Condition {

    private String expression;

    protected String expression() {
        return expression;
    }

    public AbstractCondition expression(String expression) {
        this.expression = expression;
        return this;
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
