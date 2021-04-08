package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
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
    private Connection connect = Util.getConnectionToDatabase();


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement2 = connect.createStatement()) {
            statement2.executeUpdate(create);
            System.out.println("Таблица создана успешно");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void dropUsersTable() {
        try (Statement statement2 = connect.createStatement()) {
            statement2.executeUpdate(drop);
            System.out.println("Таблица удалена");
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("Соединение закрыто");
    }

    public void saveUser(String name, String lastName, byte age) {

        String INSERT = "INSERT INTO users VALUES(id,?,?,?)";
        Transaction transaction;

        try (PreparedStatement preparedStatement = connect.prepareStatement(INSERT)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            Util.getConnectionToDatabase().commit();
        } catch (SQLException | NullPointerException ex){
            ex.printStackTrace();
            try {
                Util.getConnectionToDatabase().rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                try {
                    connect.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    public void removeUserById(long id) {
        try (Statement   statement2 = connect.createStatement()) {
            statement2.executeUpdate(String.format(remove, id));
            System.out.println("удаление юзера под id= " + id + " выполнено");
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("Соединение закрыто");
    }

    public List<User> getAllUsers() {
        List<User> list = null;
        try (Statement statement2 = connect.createStatement()) {
            list = new ArrayList<>();
            ResultSet resultSet = statement2.executeQuery(showALL);
            while (resultSet.next()) {
                list.add(new User(resultSet.getString("name")
                        , resultSet.getString("lastname")
                        , resultSet.getByte("age")));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("Соединение закрыто");
        return list;
    }

    public void cleanUsersTable() {
        try (Statement statement2 = connect.createStatement()) {
            statement2.executeUpdate(clean);
            System.out.println("Удаление выполнено");
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("Соединение закрыто");
    }
}
