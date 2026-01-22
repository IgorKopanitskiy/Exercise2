package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Util {

    public static SessionFactory getSession() {
        Configuration configuration = new Configuration();

        configuration
                .addAnnotatedClass(User.class)
                .addPackage("jm.task.core.jdbc.model")
                .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                .setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/project_db")
                .setProperty("hibernate.connection.username", "root")
                .setProperty("hibernate.connection.password", "Forpost3034!")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.hbm2ddl.auto", "update");
//Здесь ставить update / create-drop?
        return configuration.buildSessionFactory();
    }
}
