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
    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;
    private static String pass = "Wusleng567706";
    private static String user = "root";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC";
    private static Connection connection;

    public static Connection getConnectionToDatabase() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL, user, pass);
                System.out.println("Соединение с СУБД выполнено.");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Properties properties = new Properties();
                properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                properties.put(Environment.URL, DB_URL);
                properties.put(Environment.USER, user);
                properties.put(Environment.PASS, pass);
                properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                properties.put(Environment.SHOW_SQL, true);
                properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                properties.put(Environment.ORDER_UPDATES, "true");
                properties.put(Environment.HBM2DDL_AUTO, "update");

                Configuration configuration = new Configuration();
                configuration.setProperties(properties);
                configuration.addAnnotatedClass(User.class);

                serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                System.out.println("SessionFactory - создан");
            } catch (Exception e) {
                System.out.println("SessionFactory - НЕ создан");
                e.printStackTrace();
                StandardServiceRegistryBuilder.destroy(serviceRegistry);
            }
        }
        return sessionFactory;
    }

}

