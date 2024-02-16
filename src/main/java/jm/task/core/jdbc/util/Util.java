package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД

    //private static final String connection = null;
    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String USER_NAME = "trab";
    private static final String PASSWORD = "trab";


    /*public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/

    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().addAnnotatedClass(User.class);

                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties properties = new Properties();
                properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                properties.put(Environment.URL, URL);
                properties.put(Environment.USER, USER_NAME);
                properties.put(Environment.PASS, PASSWORD);
                properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                properties.put(Environment.SHOW_SQL, "true");
                properties.put(Environment.HBM2DDL_AUTO, "update");

                configuration.setProperties(properties);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}