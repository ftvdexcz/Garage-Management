package com.garagemanagement.carrepairservice.service;

import com.garagemanagement.carrepairservice.common.entity.Car;
import com.garagemanagement.carrepairservice.common.entity.CarRepair;
import com.garagemanagement.carrepairservice.common.model.CarRepairDTO;

import java.util.Map;

public interface CarRepairService {
    CarRepair createCarRepair(CarRepairDTO carRepairDTO);

    CarRepair getCarRepairById(String id);

    void deleteCarRepairById(String id);

    CarRepair updateCarRepairById(String id, Map<String, Object> fields);
}
