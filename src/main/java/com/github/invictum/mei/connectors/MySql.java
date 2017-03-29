package com.github.invictum.mei.connectors;

import com.github.invictum.mei.Backend;
import com.github.invictum.mei.dtos.Queue;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

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

}
