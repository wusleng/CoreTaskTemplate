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
    public static Connection getConnectionToDatabase() throws ClassNotFoundException, SQLException {
        //Connect in different ways - коннект разными способами
        final String DB_URL = "jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC"; // connect with jdbc
        final String DB_Driver = "java.sql.Driver";

        Connection connection = DriverManager.getConnection(DB_URL, "root", "Wusleng567706");
        System.out.println("Соединение с СУБД выполнено.");

        return connection;

    }

    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;

    // Hibernate connection
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Properties properties = new Properties();
                properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                properties.put(Environment.URL, "jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC");
                properties.put(Environment.USER, "root");
                properties.put(Environment.PASS, "Wusleng567706");
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

