package com.github.invictum.mei.conditions;

import com.github.invictum.mei.ConditionPool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConditionProcessor {

    private boolean isValid = false;
    private boolean isEmpty = false;

    public ConditionProcessor(String condition) throws IllegalArgumentException {
        if (condition == null) {
            isEmpty = true;
        } else {
            Pattern pattern = Pattern.compile("^([\\S\\s]*?)(?:\\:)([\\S\\s]*)$");
            Matcher matcher = pattern.matcher(condition);
            if (matcher.find()) {
                String type = matcher.group(1).trim();
                String expression = matcher.group(2).trim();
                isValid = ConditionPool.getInstance().getCondition(type).checkCondition(expression);
            } else {
                throw new IllegalArgumentException(String.format("Invalid condition format provided: '%s'", condition));
            }
        }
    }

    public boolean isApplicable() {
        return isEmpty || isValid;
    }
}
