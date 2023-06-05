package com.garagemanagement.carrepairservice.service.impl;

import com.garagemanagement.carrepairservice.common.entity.Car;
import com.garagemanagement.carrepairservice.common.entity.CarRepair;
import com.garagemanagement.carrepairservice.common.handler.AppError;
import com.garagemanagement.carrepairservice.common.model.CarRepairDTO;
import com.garagemanagement.carrepairservice.common.utils.GenerateUUID;
import com.garagemanagement.carrepairservice.internal.InternalServiceImpl;
import com.garagemanagement.carrepairservice.repository.CarRepairRepository;
import com.garagemanagement.carrepairservice.repository.CarRepository;
import com.garagemanagement.carrepairservice.service.CarRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Service
public class CarRepairServiceImpl implements CarRepairService {
    @Autowired
    CarRepairRepository carRepairRepository;

    @Autowired
    InternalServiceImpl internalService;

    @Autowired
    CarRepository carRepository;

    @Override
    public CarRepair createCarRepair(CarRepairDTO carRepairDTO) {
        if(carRepairDTO.getStatus() == null){
            carRepairDTO.setStatus(false);
        }

        if(carRepairDTO.getEmployeeId() != null)
            internalService.checkUserRoleById(carRepairDTO.getEmployeeId(), "SUPPORT");

        Optional<Car> car = carRepository.findById(carRepairDTO.getCarId());

        if(car.isEmpty())
            throw AppError.NotFoundError(Car.class.getSimpleName());

        String uuid = GenerateUUID.generateRandomUUID();

        carRepairDTO.setId(uuid);

        CarRepair carRepair = new CarRepair(
                carRepairDTO.getId(),
                carRepairDTO.getStatus(),
                carRepairDTO.getDate(),
                carRepairDTO.getNote(),
                carRepairDTO.getEmployeeId(),
                car.get()
        );

        return carRepairRepository.save(carRepair);
    }

    @Override
    public CarRepair getCarRepairById(String id) {
        Optional<CarRepair> carRepair = carRepairRepository.findById(id);

        if(carRepair.isEmpty())
            throw AppError.NotFoundError(CarRepair.class.getSimpleName());

        return carRepair.get();
    }

    @Override
    public void deleteCarRepairById(String id) {
        Optional<CarRepair> carRepair = carRepairRepository.findById(id);

        if(carRepair.isEmpty())
            throw AppError.NotFoundError(CarRepair.class.getSimpleName());

        carRepairRepository.deleteById(id);
    }

    @Override
    public CarRepair updateCarRepairById(String id, Map<String, Object> fields) {
        Optional<CarRepair> carRepair = carRepairRepository.findById(id);

        if(carRepair.isEmpty())
            throw AppError.NotFoundError(CarRepair.class.getSimpleName());

        fields.forEach((key, value) -> {
            if (!key.equals("id")) {
                Field field = ReflectionUtils.findField(CarRepair.class, key);
                if (field != null) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, carRepair.get(), value);
                }
            }
        });

        internalService.checkUserRoleById(carRepair.get().getEmployeeId(), "SUPPORT");

        Optional<Car> car = carRepository.findById(carRepair.get().getCar().getId());

        if(car.isEmpty())
            throw AppError.NotFoundError(Car.class.getSimpleName());

        System.out.println(carRepair.get());

        return carRepairRepository.save(carRepair.get());
    }
}
