package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Main {

        public static void main(String[] args) throws SQLException, ClassNotFoundException {
            UserService userServ = new UserServiceImpl();
            userServ.createUsersTable();
            userServ.saveUser("John", "Nikson", (byte) 56);
            userServ.saveUser("Sarah", "Konor", (byte) 34);
            userServ.saveUser("Peter", "Mexes", (byte) 33);
            userServ.saveUser("Leam", "Howlet", (byte) 51);

            System.out.println(userServ.getAllUsers());

            userServ.removeUserById(2);
            userServ.cleanUsersTable();
            userServ.dropUsersTable();
            Util.getConnectionToDatabase().close();

 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            UserDaoHibernateImpl userHib = new UserDaoHibernateImpl();
            Util.getSessionFactory();
            userHib.createUsersTable();
            userHib.saveUser("John", "Nikson", (byte) 56);
            userHib.saveUser("Sarah", "Konor", (byte) 34);
            userHib.saveUser("Peter", "Mexes", (byte) 33);
            userHib.saveUser("Leam", "Howlet", (byte) 51);

            System.out.println(userHib.getAllUsers());

            userHib.removeUserById(3);
            userHib.cleanUsersTable();
            userHib.dropUsersTable();
            Util.getSessionFactory().close();

    }

}
