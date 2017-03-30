package com.github.invictum.mei.connectors;

import com.github.invictum.mei.Backend;
import com.github.invictum.mei.MeiPlugin;
import com.github.invictum.mei.dtos.Queue;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.bukkit.Bukkit;
import org.sql2o.Connection;
import org.sql2o.Query;
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
        try (Connection con = connection.beginTransaction()) {
            Query query = con.createQuery(sql);
            for (String id : ids) {
                query.addParameter("id", id).addToBatch();
            }
            query.executeBatch();
            con.commit();
        }
    }

    private Boolean createDefaultTables() {
        String tables;
        try {
            tables = Resources.toString(Resources.getResource(MeiPlugin.class, "/schemes.sql"), Charsets.UTF_8)
                    .replace("{queue_table_name}", getProperty("queue_table"))
                    .replace("{schedule_table_name}", getProperty("schedule_table"));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        for (String table : tables.split(";")) {
            if (!table.isEmpty()) {
                try (Connection con = connection.open()) {
                    con.createQuery(table).executeUpdate();
                } catch (Exception ex) {
                    return false;
                }
            }
        }

        return true;
    }

}
