package com.github.invictum.mei.conditions;

import com.github.invictum.mei.conditions.instance.Condition;
import com.github.invictum.mei.conditions.instance.GameTime;
import com.github.invictum.mei.conditions.instance.ServerTime;
import com.github.invictum.mei.conditions.instance.UserCount;
import com.github.invictum.mei.conditions.instance.UserOnline;

import java.util.Hashtable;

public class ConditionPool {
    private static ConditionPool ourInstance = new ConditionPool();
    private Hashtable<String, Condition> conditionList = null;

    private ConditionPool() {
        conditionList = new Hashtable<>();
        conditionList.put("game_time", new GameTime());
        conditionList.put("server_time", new ServerTime());
        conditionList.put("user_count", new UserCount());
        conditionList.put("user_online", new UserOnline());
    }

    public static ConditionPool getInstance() {
        return ourInstance;
    }

    public Condition getCondition(String key) throws IllegalArgumentException {
        if (conditionList.containsKey(key)) {
            return conditionList.get(key);
        }
        throw new IllegalArgumentException(String.format("Unsupported type of condition: '%s'", key));
    }
}
