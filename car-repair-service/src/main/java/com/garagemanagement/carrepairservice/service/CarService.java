package com.garagemanagement.carrepairservice.service;

import com.garagemanagement.carrepairservice.common.entity.Car;

import java.util.Map;

public interface CarService {
    Car createCar(Car car);

    Car getCarById(String id);

    void deleteCarById(String id);

    Car updateCarById(String id, Map<String, Object> fields);
}
