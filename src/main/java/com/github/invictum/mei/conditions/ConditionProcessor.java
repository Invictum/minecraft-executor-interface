package com.github.invictum.mei.conditions;

import com.github.invictum.mei.ConditionPool;
import com.github.invictum.mei.MeiPlugin;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConditionProcessor {

    private static Logger log = MeiPlugin.getPlugin(MeiPlugin.class).getLogger();
    private boolean isValid;
    private boolean isEmpty;
    private String type;
    private String expression;

    public ConditionProcessor(String condition) {
        isValid = false;
        if (condition == null) {
            isValid = true;
            isEmpty = true;
            return;
        }

        String regexp = "^([\\S\\s]*?)(?:\\:)([\\S\\s]*)$";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(condition);
        if (matcher.find()) {
            type = matcher.group(1).trim();
            expression = matcher.group(2).trim();
            if (ConditionPool.getInstance().containsKey(type)) {
                isValid = true;
            } else {
                log.warning(String.format("Unsupported type of condition, '%s'", type));
            }
        } else {
            log.warning(String.format("Invalid condition format provided: '%s'", condition));
        }
    }

    public boolean isValid() {
        return isValid;
    }

    public boolean isApplicable() {
        return isEmpty || ConditionPool.getInstance().getCondition(type).checkCondition(expression);
    }
}
