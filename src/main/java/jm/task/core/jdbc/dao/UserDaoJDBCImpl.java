package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {

    public UserDaoJDBCImpl() {

    }

    PreparedStatement preparedStatement = null;
    Statement statement = null;

    public void createUsersTable() {
        actionOnTable("CREATE TABLE IF NOT EXISTS User (Id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(30), lastname VARCHAR(30), age INT(3))");
    }

    public void dropUsersTable() {
        actionOnTable("DROP TABLE IF EXISTS User");
    }

    private void actionOnTable(String drop) {
        Connection connection = getConnection();
        try {
            statement = connection.createStatement();
            statement.execute(drop);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(statement !=null){
                    statement.close();
                }
                if(connection!=null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void saveUser(String name, String lastName, byte age) {
        Connection connection = getConnection();
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO User (name, lastname, age) VALUES (?,?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(statement !=null){
                    statement.close();
                }
                if(connection!=null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        Connection connection = getConnection();
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM User WHERE Id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(statement !=null){
                    statement.close();
                }
                if(connection!=null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        Connection connection = getConnection();
        List<User> userList = new ArrayList<>();

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM User");

            while(resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("Id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(statement !=null){
                    statement.close();
                }
                if(connection!=null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for (User s :
                userList) {
            System.out.println(s);
        }
        return userList;
    }

    public void cleanUsersTable() {
        actionOnTable("DELETE FROM User");
    }
}
