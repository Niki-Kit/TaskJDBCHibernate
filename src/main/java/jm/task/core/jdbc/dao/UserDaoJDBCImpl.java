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
    private static final String CREATE = "CREATE TABLE IF NOT EXISTS User (Id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
            "name VARCHAR(30), lastname VARCHAR(30), age INT(3))";
    private static final String DROP = "DROP TABLE IF EXISTS User";
    private static final String ADD = "INSERT INTO User (name, lastname, age) VALUES (?,?,?)";
    private static final String REMOVE = "DELETE FROM User WHERE Id = ?";
    private static final String SELECT = "SELECT * FROM User";
    private static final String DELETE_ALL = "DELETE FROM User";


    public void createUsersTable() {
        actionOnTable(CREATE);
    }

    public void dropUsersTable() {
        actionOnTable(DROP);
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
            preparedStatement = connection.prepareStatement(ADD);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.execute();
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
            preparedStatement = connection.prepareStatement(REMOVE);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();

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
            ResultSet resultSet = statement.executeQuery(SELECT);

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
        actionOnTable(DELETE_ALL);
    }
}
