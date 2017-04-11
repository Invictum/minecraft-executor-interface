package com.github.invictum.mei;

import com.github.invictum.mei.conditions.ConditionInterface;
import com.github.invictum.mei.conditions.GameTime;
import com.github.invictum.mei.conditions.ServerTime;
import com.github.invictum.mei.conditions.UserCount;
import com.github.invictum.mei.conditions.UserOnline;

import java.util.Hashtable;

public class ConditionPool {
    private static ConditionPool ourInstance = new ConditionPool();
    private Hashtable<String, ConditionInterface> conditionList = null;

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

    public ConditionInterface getCondition(String key) throws IllegalArgumentException {
        if (conditionList.containsKey(key)) {
            return conditionList.get(key);
        }
        throw new IllegalArgumentException(String.format("Unsupported type of condition: '%s'", key));
    }
}
