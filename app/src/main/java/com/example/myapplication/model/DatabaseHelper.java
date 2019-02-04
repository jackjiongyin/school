package com.example.myapplication.model;

import android.os.StrictMode;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper {

    private ConnectionSource connectionSource;

    private Map<String, Dao> daos = new HashMap<String, Dao>();

    private static DatabaseHelper instance;


    public static synchronized DatabaseHelper getHelper() {
        if (instance == null) {
            synchronized (DatabaseHelper.class) {
                if (instance == null) {
                    instance = new DatabaseHelper();
                }
            }
        }
        return instance;
    }

    private DatabaseHelper() {
        if (connectionSource == null) {
            try {
                String jdbc =  "jdbc:jtds:sqlserver://192.168.88.100:1433;databaseName=test;user=sa;password=abc@123456;";
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                connectionSource = new JdbcConnectionSource(jdbc);
            } catch (SQLException e) {
                throw new RuntimeException("Problems initializing database objects", e);
            }
        }
    }

    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();
        if (!daos.containsKey(className)) {
            dao = DaoManager.createDao(connectionSource, clazz);
            daos.put(className, dao);
        } else {
            dao = daos.get(className);
        }
        return dao;
    }

    public ConnectionSource getConnection() {
        return connectionSource;
    }

    public void close() throws IOException {
        connectionSource.close();
    }

}
