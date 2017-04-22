package com.github.invictum.mei.command;

import com.github.invictum.mei.MeiPlugin;
import com.github.invictum.mei.conditions.ConditionBuilder;
import com.github.invictum.mei.conditions.Condition;
import org.bukkit.Bukkit;

import static com.github.invictum.mei.command.Result.*;

public class Command {

    private String command;
    private Condition condition;

    public Command(String command, String conditionExpression) {
        this.command = command;
        condition = ConditionBuilder.build(conditionExpression);
    }

    public Result execute() {
        if (condition == null || !condition.isValid()) {
            return CONDITION_ERROR;
        } else if (condition.check()) {
            MeiPlugin.log().info("Processing of command: " + command);
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
            return ALL_OK;
        }
        return CONDITION_FAIL;
    }
}
