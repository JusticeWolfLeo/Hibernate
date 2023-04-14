package org.example;

import dao.CarDaoJdbc;

/**
 * Наша первое CRUD приложение с работой Базы данных
 * CRUD
 * Create - создать пользовтаеля
 * Read - считать пользователя
 * Update - обновление
 * Delete - удаление
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        CarDaoJdbc carDao = new CarDaoJdbc();
        carDao.createCarTable();
        carDao.saveCar("Mitsubishi", "Lancer", "A001", 1998);
        carDao.saveCar("Toyota", "Carola", "B002", 1999);
        carDao.saveCar("Zhigul", "Moskvich", "C003", 2000);
        System.out.println(carDao.getAllCars());
        carDao.deleteCar(2);
        System.out.println(carDao.getAllCars());
        CarDto carDto = carDao.getCarById(3);
        System.out.println("Попытка найти машину " + 3);
        System.out.println(carDto);
        carDao.cleanCarTable();
        System.out.println(carDao.getAllCars());
        carDao.cleanCarTable();
        carDao.truncateCarTable();
        carDao.dropCarTable();

    }
}
