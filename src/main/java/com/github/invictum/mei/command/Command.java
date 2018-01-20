package com.github.invictum.mei.command;

import com.github.invictum.mei.MeiPlugin;
import com.github.invictum.mei.condition.Condition;
import com.github.invictum.mei.condition.ConditionBuilder;
import com.github.invictum.mei.entity.TaskEntity;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import static com.github.invictum.mei.command.Result.*;

/**
 * Defines command model abstraction
 */
public class Command {

    private String command;
    private String sender;
    private Set<Condition> conditions;

    private static final Logger LOGGER = JavaPlugin.getPlugin(MeiPlugin.class).getLogger();

    public Command(TaskEntity task) {
        this.command = task.getCommand();
        this.sender = task.getSender();
        conditions = task.getConditions() == null ? new HashSet<>() : ConditionBuilder.build(task.getConditions());
    }

    /**
     * Try to execute command with taking into account condition state
     *
     * @return command execution {@link Result} object
     */
    public Result execute() {
        /* Verify condition */
        if (conditions.stream().anyMatch(condition -> !condition.isValid())) {
            return CONDITION_ERROR;
        }
        /* Check condition and emit command */
        if (conditions.stream().allMatch(Condition::check)) {
            LOGGER.info("Processing of command: " + command);
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
            return ALL_OK;
        }
        return CONDITION_FAIL;
    }
}
