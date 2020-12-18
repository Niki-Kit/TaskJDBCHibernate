package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao u = new UserDaoHibernateImpl();

    public void createUsersTable() {
        u.createUsersTable();
    }

    public void dropUsersTable() {
        u.dropUsersTable();
    }


    public void saveUser(String name, String lastName, byte age) {
        u.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        u.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return u.getAllUsers();
    }

    public void cleanUsersTable() {
        u.cleanUsersTable();
    }
}
