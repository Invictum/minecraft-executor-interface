package com.github.invictum.mei.connectors;

import com.github.invictum.mei.Backend;
import com.github.invictum.mei.MeiPlugin;
import com.github.invictum.mei.dtos.Queue;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.bukkit.Bukkit;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class MySql extends Backend {

    private Sql2o connection;
    private Logger log = Bukkit.getLogger();

    @Override
    public Boolean initBackend() {
        String connectionString = getProperty("connectionString", "");
        String username = getProperty("username", "");
        String password = getProperty("password", "");
        if (connectionString.isEmpty() || username.isEmpty() || password.isEmpty()) {
            log.info("You should provide 'connectionString', 'username' and 'password' in your config file");
            return false;
        }
        this.connection = new Sql2o(connectionString, username, password);

        return createDefaultTables();
    }

    @Override
    public void closeBackend() {
        //no need to close connection for sql2o, cool I think.
    }

    @Override
    public List<Queue> getQueues() {
        String sql = "SELECT * FROM " + getProperty("queue_table");
        List<Queue> queues;
        try (Connection con = connection.open()) {
            queues = con.createQuery(sql)
                    .executeAndFetch(Queue.class);
        }
        return queues;
    }

    @Override
    public void removeQueues(List<String> ids) {
        String sql = "DELETE FROM " + getProperty("queue_table") + " WHERE id=:id";
        for (String id : ids) {
            try (Connection con = connection.open()) {
                con.createQuery(sql)
                        .addParameter("id", id)
                        .executeUpdate();
            }
        }
    }

    private Boolean createDefaultTables() {
        String queueTable;
        String scheduleTable;

        try {
            queueTable = Resources.toString(Resources.getResource(MeiPlugin.class, "/queue_table.sql"), Charsets.UTF_8)
                    .replace("{queue_table_name}", getProperty("queue_table"));
            scheduleTable = Resources.toString(Resources.getResource(MeiPlugin.class, "/schedule_table.sql"), Charsets.UTF_8)
                    .replace("{schedule_table_name}", getProperty("schedule_table"));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        if (!queueTable.isEmpty()) {
            try (Connection con = connection.open()) {
                con.createQuery(queueTable).executeUpdate();
            } catch (Exception ex) {
                return false;
            }
            try (Connection con = connection.open()) {
                con.createQuery(scheduleTable).executeUpdate();
            } catch (Exception ex) {
                return false;
            }
        }
        return true;
    }

}
