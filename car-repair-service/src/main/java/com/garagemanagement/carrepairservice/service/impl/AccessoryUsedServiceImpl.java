package com.garagemanagement.carrepairservice.service.impl;

import com.garagemanagement.carrepairservice.common.entity.AccessoryUsed;
import com.garagemanagement.carrepairservice.common.entity.Car;
import com.garagemanagement.carrepairservice.common.entity.CarRepair;
import com.garagemanagement.carrepairservice.common.handler.AppError;
import com.garagemanagement.carrepairservice.common.model.AccessoryUsedDTO;
import com.garagemanagement.carrepairservice.common.utils.GenerateUUID;
import com.garagemanagement.carrepairservice.internal.InternalServiceImpl;
import com.garagemanagement.carrepairservice.repository.AccessoryUsedRepository;
import com.garagemanagement.carrepairservice.repository.CarRepairRepository;
import com.garagemanagement.carrepairservice.service.AccessoryUsedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Service
public class AccessoryUsedServiceImpl implements AccessoryUsedService {
    @Autowired
    InternalServiceImpl internalService;

    @Autowired
    AccessoryUsedRepository accessoryUsedRepository;

    @Autowired
    CarRepairRepository carRepairRepository;

    @Override
    public AccessoryUsedDTO createAccessoryUsed(AccessoryUsedDTO accessoryUsedDTO) {
        internalService.getAccessoryById(accessoryUsedDTO.getAccessoryId());

        Optional<CarRepair> carRepair = carRepairRepository.findById(accessoryUsedDTO.getCarRepairId());

        if(carRepair.isEmpty())
            throw AppError.ForeignKeyInvalid(CarRepair.class.getSimpleName());

        String uuid = GenerateUUID.generateRandomUUID();

        accessoryUsedDTO.setId(uuid);

        AccessoryUsed accessoryUsed = new AccessoryUsed(
                accessoryUsedDTO.getId(),
                accessoryUsedDTO.getQuantity(),
                accessoryUsedDTO.getAmount(),
                accessoryUsedDTO.getAccessoryId(),
                carRepair.get()
        );

        accessoryUsedRepository.save(accessoryUsed);
        return accessoryUsedDTO;
    }

    @Override
    public AccessoryUsedDTO getAccessoryUsedById(String id) {
        Optional<AccessoryUsed> accessoryUsed = accessoryUsedRepository.findById(id);

        if(accessoryUsed.isEmpty())
            throw AppError.NotFoundError(AccessoryUsed.class.getSimpleName());

        return AccessoryUsedDTO.retrieveAccessoryUsed(accessoryUsed.get());
    }

    @Override
    public void deleteAccessoryUsedById(String id) {
        Optional<AccessoryUsed> accessoryUsed = accessoryUsedRepository.findById(id);
        if (accessoryUsed.isEmpty())
            throw AppError.NotFoundError(AccessoryUsed.class.getSimpleName());

        accessoryUsedRepository.deleteById(id);
    }

    @Override
    public AccessoryUsedDTO updateAccessoryUsedById(String id, Map<String, Object> fields) {
        Optional<AccessoryUsed> accessoryUsed = accessoryUsedRepository.findById(id);
        if (accessoryUsed.isEmpty())
            throw AppError.NotFoundError(AccessoryUsed.class.getSimpleName());

        fields.forEach((key, value) -> {
            if (!key.equals("id")) {
                Field field = ReflectionUtils.findField(AccessoryUsed.class, key);
                if (field != null) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, accessoryUsed.get(), value);
                }
            }
        });

        internalService.getAccessoryById(accessoryUsed.get().getAccessoryId());

//        String carRepairId = accessoryUsed.get().getCarRepair().getId();
//
//        Optional<CarRepair> carRepair = carRepairRepository.findById(carRepairId);
//        if(carRepair.isEmpty())
//            throw AppError.BadRequestError("Car repair id is invalid: " + carRepairId);

        accessoryUsedRepository.save(accessoryUsed.get());

        return AccessoryUsedDTO.retrieveAccessoryUsed(accessoryUsed.get());
    }
}
