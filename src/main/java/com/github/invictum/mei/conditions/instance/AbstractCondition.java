package com.github.invictum.mei.conditions.instance;

import com.github.invictum.mei.conditions.Condition;

public abstract class AbstractCondition implements Condition {

    private String expression;

    protected String expression() {
        return expression;
    }

    public AbstractCondition expression(String expression) {
        this.expression = expression;
        return this;
    }
}
