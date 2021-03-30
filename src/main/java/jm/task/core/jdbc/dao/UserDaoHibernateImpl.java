package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {

        final String create = "CREATE TABLE IF NOT EXISTS user2 (\n" +
                "  id INT NOT NULL AUTO_INCREMENT,\n" +
                "  name VARCHAR(45) NOT NULL,\n" +
                "  lastName VARCHAR(45) NOT NULL,\n" +
                "  age INT(2) NULL,\n" +
                "  PRIMARY KEY (id));";;

        Transaction transaction = null;

        try(Session session = Util.getSessionFactory().openSession()){
            Query query = session.createSQLQuery(create).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();

        }catch (Exception ex){
            if(transaction != null){
                transaction.rollback();
            }

        }

//        Session session = null;
//        Transaction transaction = null;
//
//            //session = getSessionFactory().openSession();
//        session = Util.getSessionFactory().openSession();
//
//
//        Query query = session.createQuery(create);
//        transaction.commit();
//        session.close();

    }

    @Override
    public void dropUsersTable() {

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

    }

    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void cleanUsersTable() {

    }
}
