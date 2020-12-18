package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/task?useSSL=false&serverTimezone=UTC";
    private static final String USER = "Admin";
    private static final String PASS = "nikiKob1994";

    private static SessionFactory sessionFactory;

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            try {
                Properties properties = new Properties();
                properties.setProperty(Environment.URL , "jdbc:mysql://localhost:3306/task?useSSL=false&serverTimezone=UTC");
                properties.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                properties.setProperty(Environment.USER, "Admin");
                properties.setProperty(Environment.PASS, "nikiKob1994");
                Configuration configuration = new Configuration();
                configuration.addAnnotatedClass(User.class);
                configuration.addProperties(properties);
                ServiceRegistry serviceRegistry =
                        new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
