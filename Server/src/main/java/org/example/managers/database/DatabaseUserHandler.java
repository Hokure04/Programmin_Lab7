package org.example.managers.database;

import org.example.common.data.*;
import org.example.managers.UserHandler;
import org.example.util.Encryptor;

import java.sql.*;

public class DatabaseUserHandler {
    private UserHandler userHandler;
    private Connection database;

    public DatabaseUserHandler(Connection database, UserHandler userHandler) {
        this.database = database;
        this.userHandler = userHandler;
        createDBUser();
        deserial();
    }

    public void deserial() {
        try {
            ResultSet rs = this.database.createStatement().executeQuery(
                    "SELECT login FROM users"
            );
            this.userHandler.setNewCollection();
            while (rs.next()) {
                this.userHandler.addUser(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("SUS при десериализации пользователей");
        }
    }

    public void insertUser(User user) {
        try {
            PreparedStatement ps = this.database.prepareStatement(
                    "INSERT INTO users(login, password, salt)" +
                            "VALUES (?, ?, ?)"
            );
            ps.setString(1, user.getLogin());
            String salt = Encryptor.getSalt();
            ps.setString(2, Encryptor.getSHA384Password(user.getPassword(), salt));
            ps.setString(3, salt);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Произошел пиздец у insertUser");
            throw new RuntimeException(e);
        }
    }

    public String[] getPassword(String login) {
        try {
            PreparedStatement ps = this.database.prepareStatement(
                    "SELECT password, salt FROM users WHERE login = ?;"
            );
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            rs.next();
            String[] els = new String[2];
            els[0] = rs.getString(1);
            els[1] = rs.getString(2);
            return els;
        } catch (SQLException e) {
            System.out.println("Произошел пиздец у getPassword");
            throw new RuntimeException(e);
        }
    }

    public void clearCollection() {
        try {
            this.database.createStatement().executeUpdate(
                    "DELETE FROM users;"
            );
        } catch (SQLException e) {
            System.out.println("Произошел пиздец у clearCollection");
            throw new RuntimeException(e);
        }
    }

    public void createDBUser() {
        try {
            this.database.createStatement().executeUpdate(
                            "CREATE TABLE IF NOT EXISTS users(" +
                            "login varchar(50) PRIMARY KEY," +
                            "password varchar(116)," +
                            "salt varchar(16));"
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    public void setDatabase(Connection database) {
        this.database = database;
    }
}
