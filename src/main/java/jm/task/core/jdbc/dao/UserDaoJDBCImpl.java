package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {

    private static final String createUserTable = "CREATE TABLE IF NOT EXISTS mydbtest (" + "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " + "name VARCHAR(40) NOT NULL, " + "lastName VARCHAR(40) NOT NULL, " + "age TINYINT)";
    private static final String dropUsersTable = "DROP TABLE IF EXISTS mydbtest";
    private static final String saveUser = "INSERT INTO mydbtest (name, lastName, age) VALUES (?, ?, ?)";
    private static final String removeUserById = "DELETE FROM mydbtest WHERE id = ?";
    private static final String getAllUsers = "SELECT * FROM mydbtest";
    private static final String cleanUsersTable = "DELETE FROM mydbtest";






    public UserDaoJDBCImpl() {
    }


    public void createUsersTable() {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(createUserTable)) {
            preparedStatement.executeUpdate();

            System.out.println("Таблица успешно создана");
        }
             catch(SQLException e){
                System.err.println("Не удалось создать таблицу пользователей");
                e.printStackTrace();
            }

    }


    public void dropUsersTable() {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(dropUsersTable)) {

            preparedStatement.executeUpdate();

            System.out.println("Таблица пользователец успешно удалена");


        }
        catch(SQLException e) {
            System.err.println("Не удалось удалить таблицу пользователей");
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(saveUser)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();

            System.out.println("User с именем - " + name + " добавлен в базу данных");
        }
        catch(SQLException e) {
            System.err.println("Не удалось добавить пользователя");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(removeUserById)) {

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();

            System.out.println("Пользователь с ID " + id + " успешно удален");

        }
        catch(SQLException e) {
            System.err.println("Не удалось удалить пользователя с ID" + id);
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getAllUsers)) {
             ResultSet resultSet = preparedStatement.executeQuery();
            //List<User> userList = new ArrayList<>();

            while (resultSet.next()) {

                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");

                User user = new User(name, lastName, age);
                userList.add(user);

                System.out.println("Name: " + name + ", Last Name: " + lastName + ", Age: " + age);

            }
            System.out.println("Все записи в таблице успешно получены");
        }
        catch(SQLException e) {
            System.err.println("Не удалось получить все записи из таблицы");
            e.printStackTrace();
            //throw new RuntimeException(e);
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(cleanUsersTable)) {

            preparedStatement.executeUpdate();

            System.out.println("Все записи в таблице успешно удалены");

        }
        catch(SQLException e) {
            System.err.println("Не удалось очистить записи в таблице пользователей");
            e.printStackTrace();
        }

    }
}
