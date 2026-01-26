package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;


public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        this.sessionFactory = Util.getSessionFactory();
    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("CREATE TABLE " +
                            "IF NOT EXISTS user" +
                            " (id INT PRIMARY KEY AUTO_INCREMENT," +
                            " name VARCHAR(40)," +
                            " lastName VARCHAR(40)," +
                            " age TINYINT UNSIGNED)")
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Ошибка при создании таблицы User-ов");
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS user")
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Ошибка при удалении таблицы User-ов");
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            //Добавление студентов
            session.persist(new User(name, lastName, age));
            transaction.commit();

        } catch (Exception e) {
            System.out.println("Ошибка при добавлении User в таблицу");
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            //Удаление строки(объекта) из базы данных

            User userForDelete = session.get(User.class, id);
            session.remove(userForDelete);
            transaction.commit();

        } catch (Exception e) {
            System.out.println("Ошибка при удалении User из таблицы");
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            List<User> allUsers = session.createNativeQuery("SELECT *from user", User.class)
                    .list();
            System.out.println(allUsers);
            return allUsers;
        } catch (Exception e) {
            System.out.println("Ошибка при получении списка всех User-ов");
            throw e;
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE user")
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Ошибка при очищении таблицы User-ов");
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
