package dao;

import org.example.CarDto;
import org.example.Config;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.resource.transaction.spi.TransactionStatus.ACTIVE;
import static org.hibernate.resource.transaction.spi.TransactionStatus.MARKED_ROLLBACK;

public class CarDaoHibernate implements CarDao {

    public void createCarTable() {
        Transaction transaction = null;
        try (Session session = Config.sessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS cars(id int primary key auto_increment, stamp varchar(40), " +
                    "model varchar(40), state_number varchar(40), age int )");
            query.executeUpdate();
            transaction.commit();
            System.out.println("Нам удалось успешно создать таблицу машин");
        } catch (Exception e) {
            if (transaction != null || transaction.getStatus() == ACTIVE
                    || transaction.getStatus() == MARKED_ROLLBACK) {
                transaction.rollback();
                System.out.println("Транзакция создания отменена");
            }
        }
    }

    public void truncateCarTable() {
        Transaction transaction = null;
        try (Session session = Config.sessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery("TRUNCATE TABLE cars");
            query.executeUpdate();
            transaction.commit();
            System.out.println("Нам удалось успешно очистить таблицу машин");
        } catch (Exception e) {
            if (transaction != null || transaction.getStatus() == ACTIVE
                    || transaction.getStatus() == MARKED_ROLLBACK) {
                transaction.rollback();
                System.out.println("Транзакция очищения отменена");
            }
        }
    }

    public void dropCarTable() {
        Transaction transaction = null;
        try (Session session = Config.sessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery("DROP TABLE IF EXISTS cars");
            query.executeUpdate();
            transaction.commit();
            System.out.println("Нам удалось успешно удалить таблицу машин");
        } catch (Exception e) {
            if (transaction != null || transaction.getStatus() == ACTIVE
                    || transaction.getStatus() == MARKED_ROLLBACK) {
                transaction.rollback();
                System.out.println("Транзакция удаления отменена");
            }
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
