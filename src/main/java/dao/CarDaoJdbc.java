package dao;

import entity.Car;
import org.example.CarDto;
import org.example.Config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarDaoJdbc implements CarDao {

    public void createCarTable() {
        try (Connection connection = Config.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS cars(id int primary key auto_increment, stamp varchar(40), " +
                    "model varchar(40), state_number varchar(40), age int )");
            System.out.println("Нам удалось успешно создать таблицу машин");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void truncateCarTable() {
        try (Connection connection = Config.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE TABLE cars");
            System.out.println("Нам удалось успешно очистить таблицу машин");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropCarTable() {
        try (Connection connection = Config.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS cars");
            System.out.println("Нам удалось успешно удалить таблицу машин");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveCar(String stamp, String model, String state_number, int age) {
        final String INSERT_NEW_CAR = "INSERT INTO cars(stamp, model, state_number, age)"
                + " VALUES(?,?,?,?)";
        try (Connection connection = Config.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_NEW_CAR)) {
            statement.setString(1, stamp);
            statement.setString(2, model);
            statement.setString(3, (state_number));
            statement.setInt(4, age);
            statement.execute();
            System.out.println("Удалось создать машины:" + stamp + " ," + model + " ," + state_number + " ," + age);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCar(int id) {
        final String DELETE_CAR = "DELETE FROM cars WHERE id = ?";
        try (Connection connection = Config.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_CAR)) {
            statement.setInt(1, id);
            statement.execute();
            System.out.println("Удалось удалить машину:" + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CarDto> getAllCars() {
        List<CarDto> cars = new ArrayList<>();
        try (Connection connection = Config.getConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM cars");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String stamp = resultSet.getString("stamp");
                String model = resultSet.getString("model");
                String state_number = resultSet.getString("state_number");
                int age = resultSet.getInt("age");
                CarDto car = new CarDto(id, stamp, model, state_number, age);
                cars.add(car);
            }
            return cars;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public CarDto getCarById(int id) {
        final String GET_CAR = "SELECT * FROM cars WHERE id = ?";
        CarDto carDto = null;
        try (Connection connection = Config.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_CAR)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            CarDto CarDto = null;
            if (resultSet.next()) {
                String stamp = resultSet.getString("stamp");
                String model = resultSet.getString("model");
                String state_number = resultSet.getString("state_number");
                int age = resultSet.getInt("age");
                carDto = new CarDto(id, stamp, model, state_number, age);
            }
            return carDto;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanCarTable() {
        final String DELETE_ALL_CARS = "DELETE FROM cars";
        try (Connection connection = Config.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL_CARS)) {
            int rez = preparedStatement.executeUpdate();
            if (rez != 0) {
                System.out.println("Нам удалось удалить " + rez + " машин");
            } else {
                System.out.println("Таблица и так была пуста");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
