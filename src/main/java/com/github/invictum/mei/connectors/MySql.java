package com.github.invictum.mei.connectors;

import com.github.invictum.mei.Backend;
import com.github.invictum.mei.MeiPlugin;
import com.github.invictum.mei.dtos.Queue;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.io.IOException;
import java.util.List;

public class MySql extends Backend {

    private Sql2o connection;

    @Override
    public void initBackend() {
        String connectionUrl = getProperty("connectionUrl", null);
        String username = getProperty("username");
        String password = getProperty("password");
        if (connectionUrl == null) {
            String hostname = getProperty("hostname");
            String port = getProperty("port");
            String database = getProperty("database");
            connectionUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + database;
        }
        this.connection = new Sql2o(connectionUrl, username, password);
        createDefaultTables();
    }

    @Override
    public void closeBackend() {
        //no need to close connection for sql2o, cool I think.
    }

    @Override
    public List<Queue> getQueues() {
        String sql = "SELECT * FROM :table";
        List<Queue> queues;
        try (Connection con = connection.open()) {
            queues = con.createQuery(sql)
                    .addParameter("table", getProperty("queue_table"))
                    .executeAndFetch(Queue.class);
        }
        return queues;
    }

    @Override
    public void removeQueues(List<String> ids) {
        String sql = "DELETE FROM :table WHERE id=:id";
        for (String id : ids) {
            try (Connection con = connection.open()) {
                con.createQuery(sql)
                        .addParameter("table", getProperty("queue_table"))
                        .addParameter("id", id)
                        .executeUpdate();
            }
        }
    }

    private void createDefaultTables() {
        String queueTable = "";
        String scheduleTable = "";

        try {
            queueTable = Resources.toString(Resources.getResource(MeiPlugin.class, "/template.sql"), Charsets.UTF_8)
                    .replace("{queue_table}", getProperty("queue_table"))
                    .replace("{schedule_table}", getProperty("schedule_table"));
            scheduleTable = Resources.toString(Resources.getResource(MeiPlugin.class, "/template.sql"), Charsets.UTF_8)
                    .replace("{queue_table}", getProperty("queue_table"))
                    .replace("{schedule_table}", getProperty("schedule_table"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!queueTable.isEmpty()) {
            try (Connection con = connection.open()) {
                con.createQuery(queueTable).executeUpdate();
            }
            try (Connection con = connection.open()) {
                con.createQuery(scheduleTable).executeUpdate();
            }
        }
    }

}
