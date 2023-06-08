package com.garagemanagement.carrepairservice.service;

import com.garagemanagement.carrepairservice.common.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface CarService {
    Car createCar(Car car);

    Car getCarById(String id);

    void deleteCarById(String id);

    Car updateCarById(String id, Map<String, Object> fields);

    Page<Car> getAllCars(String customerId, Pageable pageable);
}
