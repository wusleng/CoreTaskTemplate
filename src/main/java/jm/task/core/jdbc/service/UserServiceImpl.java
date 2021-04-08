package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    UserDao usd = new UserDaoJDBCImpl();

    public UserServiceImpl() {
    }

    public void createUsersTable() {
        usd.createUsersTable();
    }

    public void dropUsersTable()  {
        usd.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        usd.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        usd.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return usd.getAllUsers();
    }

    public void cleanUsersTable(){
        usd.cleanUsersTable();
    }
}
