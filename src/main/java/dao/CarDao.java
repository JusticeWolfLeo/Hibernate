package dao;

import org.example.CarDto;

import java.util.List;

public interface CarDao {
void createCarTable();
void truncateCarTable();
     void dropCarTable();
    void saveCar(String stamp, String model, String state_number, int age);
    void deleteCar(int id);
    List<CarDto> getAllCars();
    CarDto getCarById(int id);
    void cleanCarTable();
}
