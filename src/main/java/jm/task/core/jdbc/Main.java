package jm.task.core.jdbc;

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

        SessionFactory sf = Util.getSessionFactory();

        Session session = sf.openSession();

        session.getTransaction().begin();

        User user_1 = new User();
        user_1.setName("John");
        user_1.setLastName("Nikson");
        user_1.setAge((byte) 56);

        User user_2 = new User();
        user_2.setName("Sarah");
        user_2.setLastName("Konor");
        user_2.setAge((byte) 34);

        User user_3 = new User();
        user_3.setName("Peter");
        user_3.setLastName("Mexes");
        user_3.setAge((byte) 33);

        User user_4 = new User();
        user_4.setName("Leam");
        user_4.setLastName("Howlet");
        user_4.setAge((byte) 51);

                //        UserService userServ = new UserServiceImpl();
//        userServ.createUsersTable();
//        userServ.saveUser("John", "Nikson", (byte) 56);
//        userServ.saveUser("Sarah", "Konor", (byte) 34);
//        userServ.saveUser("Peter", "Mexes", (byte) 33);
//        userServ.saveUser("Leam", "Howlet", (byte) 51);
//
//        System.out.println(userServ.getAllUsers());
//
//        userServ.removeUserById(2);
//        userServ.cleanUsersTable();
//        userServ.dropUsersTable();
//
//        Util.getConnectionToDatabase().close();

    }

}


