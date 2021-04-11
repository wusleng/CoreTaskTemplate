package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
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
    private final String INSERT = "INSERT INTO user2 VALUES(id,?,?,?)";
    private Connection connect = Util.getConnectionToDatabase();


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement2 = connect.createStatement()) {
            connect.setAutoCommit(false);
            statement2.executeUpdate(create);
            System.out.println("Таблица создана успешно");
            connect.commit();
        } catch (Exception ex) {
            try {
                connect.rollback();
                System.out.println("таблица не создалась");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            System.out.println(ex);
        } finally {
            try {
                connect.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        try (Statement statement2 = connect.createStatement()) {
            connect.setAutoCommit(false);
            statement2.executeUpdate(drop);
            System.out.println("Таблица удалена");
            connect.commit();
        } catch (Exception ex) {
            try {
                connect.rollback();
                System.out.println();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            System.out.println(ex);
        } finally {
            try {
                connect.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        System.out.println("Соединение закрыто");
    }

    public void saveUser(String name, String lastName, byte age) {

        try (PreparedStatement preparedStatement = connect.prepareStatement(INSERT)) {
            connect.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            connect.commit();
        } catch (SQLException | NullPointerException throwables) {
            try {
                connect.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            try {
                connect.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connect.prepareStatement(remove)) {
            connect.setAutoCommit(false);
            preparedStatement.executeUpdate(String.format(remove, id));
            System.out.println("удаление юзера под id= " + id + " выполнено");
            connect.commit();
        } catch (Exception ex) {
            try {
                connect.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            ex.printStackTrace();
        } finally {
            try {
                connect.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        System.out.println("Соединение закрыто");
    }

    public List<User> getAllUsers() {

        List<User> list = null;

        try (Statement statement2 = connect.createStatement()) {
            connect.setAutoCommit(false);
            list = new ArrayList<>();
            ResultSet resultSet = statement2.executeQuery(showALL);
            while (resultSet.next()) {
                list.add(new User(resultSet.getString("name")
                        , resultSet.getString("lastname")
                        , resultSet.getByte("age")));
            }
            connect.commit();
        } catch (Exception ex) {
            try {
                connect.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                try {
                    connect.setAutoCommit(true);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            ex.printStackTrace();
        }
        System.out.println("Соединение закрыто");
        return list;
    }

    public void cleanUsersTable() {
        try (Statement statement2 = connect.createStatement()) {
            connect.setAutoCommit(false);
            statement2.executeUpdate(clean);
            System.out.println("Удаление выполнено");
            connect.commit();
        } catch (Exception ex) {
            try {
                connect.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                try {
                    connect.setAutoCommit(true);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            ex.printStackTrace();
        }
        System.out.println("Соединение закрыто");
    }
}
