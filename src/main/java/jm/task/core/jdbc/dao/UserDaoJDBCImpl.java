package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement2 = Util.getConnectionToDatabase().createStatement()) {
            statement2.executeUpdate(create);
            System.out.println("Таблица создана успешно");
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("statement закрыт");
    }

    public void dropUsersTable() {
        try (Statement statement2 = Util.getConnectionToDatabase().createStatement()) {
            statement2.executeUpdate(drop);
            System.out.println("Таблица удалена");
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("Соединение закрыто");
    }

    public void saveUser(String name, String lastName, byte age) {
        final String save = "insert user2 (name, lastName, age) values " +
                "(\'" + name + "\', \'" + lastName + "\', " + age + ");";

        try (Statement statement2 = Util.getConnectionToDatabase().createStatement()) {
            statement2.executeUpdate(save);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("Соединение закрыто");

    }

    public void removeUserById(long id) {
        try (Statement statement2 = Util.getConnectionToDatabase().createStatement()) {
            statement2.executeUpdate(String.format(remove, id));
            System.out.println("удаление юзера под id= " + id + " выполнено");
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("Соединение закрыто");
    }

    public List<User> getAllUsers() {
        List<User> list = null;
        try (Statement statement2 = Util.getConnectionToDatabase().createStatement()) {
            list = new ArrayList<>();
            ResultSet resultSet = statement2.executeQuery(showALL);
            while (resultSet.next()) {
                String name;
                String lastname;
                byte age;
                name = resultSet.getString("name");
                lastname = resultSet.getString("lastname");
                age = resultSet.getByte("age");
                list.add(new User(name, lastname, age));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("Соединение закрыто");
        return list;
    }

    public void cleanUsersTable() {
        try (Statement statement2 = Util.getConnectionToDatabase().createStatement()) {
            statement2.executeUpdate(clean);
            System.out.println("Удаление выполнено");
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("Соединение закрыто");
    }
}
