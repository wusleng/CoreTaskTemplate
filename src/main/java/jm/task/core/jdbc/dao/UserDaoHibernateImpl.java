package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final String create = "CREATE TABLE IF NOT EXISTS user2" +
            "(id BIGINT not NULL AUTO_INCREMENT ," +
            " name VARCHAR(50) not NULL," +
            " lastname VARCHAR(50) not NULL, " +
            " age TINYINT not NULL," +
            " PRIMARY KEY (id))";

    private final String remove = "DELETE FROM user2 WHERE id=%d;";
    private final String showALL = "SELECT * FROM user2;";
    private final String clean = "DELETE FROM user2;";
    private final String drop = "TRUNCATE user2;";
    Transaction transaction = null;


    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {

        try (Session session = Util.getSessionFactory().openSession()) {
            Query query = session.createSQLQuery(create).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession();) {
            transaction = session.beginTransaction();
            session.createSQLQuery(drop).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        final String save = "insert user2 (name, lastName, age) values " +
                "(\'" + name + "\', \'" + lastName + "\', " + age + ");";

        try (Session session = Util.getSessionFactory().openSession();) {
            Query query = session.createSQLQuery(save).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession();) {
            Query query = session.createSQLQuery(remove).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession();) {
            list = (List<User>) session.createQuery("From User").list();
            transaction.commit();

        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession();) {
            Query query = session.createSQLQuery(clean).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
