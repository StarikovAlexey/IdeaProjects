package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connection = Util.getConnection();

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS user (id INT PRIMARY KEY AUTO_INCREMENT NOT NULL," +
                "name VARCHAR(45)," +
                "lastname VARCHAR(45)," +
                "age TINYINT NOT NULL)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            statement.execute();
            connection.commit();
            System.out.println("������� �������");
        } catch (SQLException a) {
            a.printStackTrace();
            System.err.println("������ �������� �������");
            try {
                connection.rollback();
            } catch (SQLException b) {
                b.printStackTrace();
                System.out.println("������ ������");
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException c) {
                    c.printStackTrace();
                }
            }
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS user";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
            System.out.println("������� �������");
        } catch (SQLException a) {
            a.printStackTrace();
            System.err.println("������ �������� �������");
        }
    }



    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO user(name, lastname, age) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("������������" + name + " " + lastName + " ��������");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("������ ���������� ������������");
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM user WHERE ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();
            System.out.println("������������ " + id + " ������");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("������ �������� ������������");
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM user";
        List<User> allUsers = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                allUsers.add(user);
            }
            if (!allUsers.isEmpty()) {
                System.out.println("������ ���� �������������: \n" + allUsers);
            } else {
                System.out.println("������ ����");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("�� ������� �������� ���� ������������� � ��");
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE user";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
            System.out.println("������� �������");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("�� ������� �������� �������");
        }
    }
}
