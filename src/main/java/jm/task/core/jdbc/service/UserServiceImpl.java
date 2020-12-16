package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl extends Util implements UserService {

    Connection connection = getConnection();
    PreparedStatement preparedStatement = null;
    Statement statement = null;
    private static final String CREATE = "CREATE TABLE IF NOT EXISTS User (Id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
            "name VARCHAR(30), lastname VARCHAR(30), age INT(3))";
    private static final String DROP = "DROP TABLE IF EXISTS User";
    private static final String ADD = "INSERT INTO User (Id, name, lastname, age) VALUES (?,?,?,?)";
    private static final String REMOVE = "DELETE FROM User WHERE Id = ?";
    private static final String SELECT = "SELECT * FROM User";
    private static final String DELETE_ALL = "DELETE FROM User";


    public void createUsersTable() throws SQLException {
        statement = connection.createStatement();
        statement.execute(CREATE);

        statement.close();
        connection.close();
    }

    public void dropUsersTable() throws SQLException {
        statement = connection.createStatement();
        statement.execute(DROP);

        statement.close();
        connection.close();
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        preparedStatement = connection.prepareStatement(ADD);
        preparedStatement.setInt(1,1);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, lastName);
        preparedStatement.setInt(4, age);
        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

    }

    public void removeUserById(long id) throws SQLException {
        preparedStatement = connection.prepareStatement(REMOVE);
        preparedStatement.setLong(1, id);
        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

    }

    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(SELECT);

        while(resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getLong("Id"));
            user.setName(resultSet.getString("name"));
            user.setLastName(resultSet.getString("lastname"));
            user.setAge(resultSet.getByte("age"));

            userList.add(user);
        }

        statement.close();
        connection.close();

        return userList;
    }

    public void cleanUsersTable() throws SQLException {
        statement = connection.createStatement();
        statement.executeUpdate(DELETE_ALL);

        statement.close();
        connection.close();

    }
}
