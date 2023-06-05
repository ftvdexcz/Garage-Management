package com.garagemanagement.carrepairservice.service;

import com.garagemanagement.carrepairservice.common.entity.AccessoryUsed;
import com.garagemanagement.carrepairservice.common.entity.Car;
import com.garagemanagement.carrepairservice.common.model.AccessoryUsedDTO;

import java.util.Map;

public interface AccessoryUsedService {
    AccessoryUsedDTO createAccessoryUsed(AccessoryUsedDTO accessoryUsedDTO);

    AccessoryUsedDTO getAccessoryUsedById(String id);

    void deleteAccessoryUsedById(String id);

    AccessoryUsedDTO updateAccessoryUsedById(String id, Map<String, Object> fields);
}
