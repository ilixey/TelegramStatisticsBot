package com.telegrambotwebapp.services;

import com.telegrambotwebapp.entities.ActivityEntity;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DatabaseService {
    private final String url = "jdbc:postgresql://34.116.188.24:5432/testdb";
    private final String login = "vaspiakou";
    private final String password = "vaspiakou";

    private final String SELECT_ACTIVITY = "select activities.id, users.name, users.surname, activities.activity, activities.duration, activities.publication_date from activities LEFT JOIN users ON activities.user_id = users.id";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, login, password);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public List<ActivityEntity> getAllActivities(){
        List<ActivityEntity> list = new LinkedList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ACTIVITY)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                list.add(new ActivityEntity(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getString("surname"), resultSet.getString("activity"), resultSet.getDouble("duration"), resultSet.getDate("publication_date")));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
