package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl extends Util implements UserDao {

    public UserDaoHibernateImpl() {

    }

    private void actionOnTable(String drop) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery(drop).executeUpdate();
            session.getTransaction().commit();
        }
    }

    public void createUsersTable() {
        actionOnTable("CREATE TABLE IF NOT EXISTS User (Id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(30), lastname VARCHAR(30), age INT(3))");
    }

    public void dropUsersTable() {
        actionOnTable("DROP TABLE IF EXISTS User");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            System.out.println("User с именем – " + name + " добавлен в базу данных");
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
       List<User> userList = Util.getSessionFactory().openSession().createQuery("from User").list();
        for (User s :
                userList) {
            System.out.println(s);
        }
       return userList;
    }

    @Override
    public void cleanUsersTable() {
        actionOnTable("DELETE FROM User");
    }
}
