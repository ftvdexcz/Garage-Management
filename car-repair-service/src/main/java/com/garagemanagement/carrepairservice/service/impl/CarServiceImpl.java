package com.garagemanagement.carrepairservice.service.impl;

import com.garagemanagement.carrepairservice.common.entity.Car;
import com.garagemanagement.carrepairservice.common.handler.AppError;
import com.garagemanagement.carrepairservice.common.utils.GenerateUUID;
import com.garagemanagement.carrepairservice.internal.InternalServiceImpl;
import com.garagemanagement.carrepairservice.repository.CarRepository;
import com.garagemanagement.carrepairservice.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    CarRepository carRepository;

    @Autowired
    InternalServiceImpl internalService;

    @Override
    public Car createCar(Car car) {
        Optional<Car> c = carRepository.findByPlate(car.getPlate());

        if (c.isPresent())
            throw AppError.ExistedNameError(Car.class.getSimpleName(), car.getPlate());

        internalService.checkUserRoleById(car.getCustomerId(), "CUSTOMER");

        String uuid = GenerateUUID.generateRandomUUID();

        car.setId(uuid);

        return carRepository.save(car);
    }

    @Override
    public Car getCarById(String id) {
        Optional<Car> car = carRepository.findById(id);

        if(car.isEmpty())
            throw AppError.NotFoundError(Car.class.getSimpleName());

        return car.get();
    }

    @Override
    public void deleteCarById(String id) {
        Optional<Car> car = carRepository.findById(id);
        if (car.isEmpty())
            throw AppError.NotFoundError(Car.class.getSimpleName());

        carRepository.deleteById(id);
    }

    @Override
    public Car updateCarById(String id, Map<String, Object> fields) {
        Optional<Car> car = carRepository.findById(id);
        if (car.isEmpty())
            throw AppError.NotFoundError(Car.class.getSimpleName());

        fields.forEach((key, value) -> {
            if (!key.equals("id")) {
                Field field = ReflectionUtils.findField(Car.class, key);
                if (field != null) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, car.get(), value);
                }
            }
        });

        internalService.checkUserRoleById(car.get().getCustomerId(), "CUSTOMER");

        return carRepository.save(car.get());
    }
}
