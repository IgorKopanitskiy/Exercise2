package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static SessionFactory sessionFactory;

    private Util() {
    }

    public static SessionFactory getSession() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();

            configuration
                    .addAnnotatedClass(User.class)
                    .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                    .setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/project_db")
                    .setProperty("hibernate.connection.username", "root")
                    .setProperty("hibernate.connection.password", "Forpost3034!")
                    .setProperty("hibernate.show_sql", "true")
                    .setProperty("hibernate.hbm2ddl.auto", "update");

            sessionFactory = configuration.buildSessionFactory();
        }
        return sessionFactory;
    }




    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project_db";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Forpost3034!";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {

        Connection connection = null;

        Class.forName(DB_DRIVER);
        connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        return connection;
    }
}
