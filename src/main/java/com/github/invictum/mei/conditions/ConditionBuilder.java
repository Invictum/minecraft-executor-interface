package com.github.invictum.mei.conditions;

import com.github.invictum.mei.conditions.instance.*;

import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConditionBuilder {

    private static Hashtable<String, AbstractCondition> conditionList = new Hashtable<>();

    static {
        conditionList = new Hashtable<>();
        conditionList.put("game_time", new GameTime());
        conditionList.put("server_time", new ServerTime());
        conditionList.put("user_count", new UserCount());
        conditionList.put("user_online", new UserOnline());
    }

    public static Condition build(String expression) {
        if (expression == null) {
            return new Default();
        }
        Matcher matcher = Pattern.compile("^\\s*(.+?)\\s*:\\s*(.+?)\\s*$").matcher(expression);
        if (matcher.find()) {
            String type = matcher.group(1);
            String clause = matcher.group(2);
            return conditionList.containsKey(type) ? conditionList.get(type).expression(clause) : null;
        }
        return null;
    }
}
