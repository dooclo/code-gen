package com.dooclo.gen.jdbc;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by IvanMa on 2014/11/25.
 */
public class ConnectionManager {

    static Connection connection = null;

    public static Connection getConnection(String dbName){
        if(connection != null){
            return connection;
        }
        Configuration jdbcProperties = null;
        try {
            jdbcProperties = new PropertiesConfiguration("conf/jdbc.properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        String driverName = jdbcProperties.getString(dbName + ".jdbc.driver");
        String url = jdbcProperties.getString(dbName + ".jdbc.url");
        String userName = jdbcProperties.getString(dbName + ".jdbc.username");
        String password = jdbcProperties.getString(dbName + ".jdbc.password");
        try {
            Class.forName(driverName).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(url,userName,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
