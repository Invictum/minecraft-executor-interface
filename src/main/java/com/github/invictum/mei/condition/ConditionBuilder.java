package com.github.invictum.mei.condition;

import com.github.invictum.mei.condition.instance.*;
import com.github.invictum.mei.entity.ConditionEntity;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Constructs a set of conditions based on provided expressions
 */
public class ConditionBuilder {

    private static Hashtable<String, AbstractCondition> conditionList = new Hashtable<>();

    static {
        conditionList.put("game_time", new GameTime());
        conditionList.put("server_time", new ServerTime());
        conditionList.put("user_count", new UserCount());
        conditionList.put("user_online", new UserOnline());
    }

    private ConditionBuilder() {
        // Disable constructor
    }

    /**
     * Assembles conditions based on expressions
     *
     * @param conditions a set of {@link ConditionEntity}s
     * @return ready conditions models set
     */
    public static Set<Condition> build(Set<ConditionEntity> conditions) {
        /* Check empty and null condition */
        if (conditions.stream().anyMatch(c -> c.getCondition() == null || c.getCondition().isEmpty())) {
            /* Error condition */
            return Collections.singleton(new SafeError());
        }
        /* Wrap entity into condition */
        return conditions.stream()
                .map(c -> conditionList.getOrDefault(c.getCondition(), new SafeError()).expression(c.getValue()))
                .filter(Objects::nonNull).collect(Collectors.toSet());
    }
}
