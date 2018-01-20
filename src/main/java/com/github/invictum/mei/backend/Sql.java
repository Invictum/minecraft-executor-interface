package com.github.invictum.mei.backend;

import com.github.invictum.mei.MeiPlugin;
import com.github.invictum.mei.Utils;
import com.github.invictum.mei.entity.ConditionEntity;
import com.github.invictum.mei.entity.TaskEntity;
import org.bukkit.plugin.java.JavaPlugin;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import java.io.InputStream;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Stores tasks details into SQL server
 */
public class Sql implements Backend {

    private static final Logger LOGGER = Utils.getLogger();

    private Sql2o database;
    private String tasksTable;
    private String conditionsTable;

    public Sql(Map<String, Object> options) {
        /* Collect properties */
        String connectionString = (String) options.getOrDefault("connectionString", "");
        String username = (String) options.getOrDefault("username", "");
        String password = (String) options.getOrDefault("password", "");
        tasksTable = (String) options.getOrDefault("tasks_table", "mei_tasks");
        conditionsTable = (String) options.getOrDefault("conditions_table", "mei_conditions");
        try {
            database = new Sql2o(connectionString, username, password);
            createDefaultTables();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.warning("Unable to establish connection with SQL server. Check configuration");
            Utils.disablePlugin();
        }
    }

    @Override
    public void store(TaskEntity task) {
        String insertTaskQuery = "INSERT INTO " + tasksTable + " (command, sender) VALUES (:command, :sender)";
        String insertConditionQuery = "INSERT INTO " + conditionsTable + " (`condition`, value, task_id) VALUES (:condition, :value, :task_id)";
        try (Connection connection = database.beginTransaction()) {
            Long taskId = connection.createQuery(insertTaskQuery).bind(task).executeUpdate().getKey(Long.class);
            if (task.getConditions() != null) {
                task.getConditions().forEach(condition -> connection.createQuery(insertConditionQuery).bind(condition)
                        .addParameter("task_id", taskId).executeUpdate());
            }
            connection.commit();
        }
    }

    @Override
    public Set<TaskEntity> list() {
        try (Connection connection = database.open()) {
            /* Load all available tasks */
            List<TaskEntity> tasks = connection.createQuery("SELECT * FROM " + tasksTable)
                    .executeAndFetch(TaskEntity.class);
            /* Populate tasks with condition */
            for (TaskEntity task : tasks) {
                List<ConditionEntity> conditionEntities = connection
                        .createQuery("SELECT `condition`, value FROM " + conditionsTable + " WHERE task_id=:task_id")
                        .addParameter("task_id", task.getId()).executeAndFetch(ConditionEntity.class);
                task.setConditions(new HashSet<>(conditionEntities));
            }
            return new HashSet<>(tasks);
        }
    }

    @Override
    public void delete(String... ids) {
        if (ids != null) {
            String sql = "DELETE FROM " + tasksTable + " WHERE id=:id";
            try (Connection connection = database.beginTransaction()) {
                Query query = connection.createQuery(sql);
                Stream.of(ids).forEach(id -> query.addParameter("id", id).addToBatch());
                query.executeBatch();
                connection.commit();
            }
        }
    }

    private void createDefaultTables() {
        /* Extract scheme file */
        InputStream stream = JavaPlugin.getPlugin(MeiPlugin.class).getResource("schemes.sql");
        Scanner scanner = new Scanner(stream).useDelimiter("\\A");
        String content = scanner.hasNext() ? scanner.next() : "";
        /* Apply creation scripts */
        try (Connection connection = database.beginTransaction()) {
            String queries[] = content.replace("{tasks_table_name}", tasksTable)
                    .replace("{conditions_table_name}", conditionsTable).split(";");
            for (String query : queries) {
                if (!query.isEmpty()) {
                    connection.createQuery(query).executeUpdate();
                }
            }
            connection.commit();
        }
    }
}
