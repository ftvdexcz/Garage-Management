package com.garagemanagement.carrepairservice.service.impl;

import com.garagemanagement.carrepairservice.common.entity.CarRepair;
import com.garagemanagement.carrepairservice.common.entity.ServiceUsed;
import com.garagemanagement.carrepairservice.common.handler.AppError;

import com.garagemanagement.carrepairservice.common.model.ServiceUsedDTO;
import com.garagemanagement.carrepairservice.common.utils.GenerateUUID;
import com.garagemanagement.carrepairservice.internal.InternalServiceImpl;
import com.garagemanagement.carrepairservice.repository.CarRepairRepository;
import com.garagemanagement.carrepairservice.repository.ServiceUsedRepository;
import com.garagemanagement.carrepairservice.service.IServiceUsed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Service
public class ServiceUsedImpl implements IServiceUsed {
    @Autowired
    InternalServiceImpl internalService;

    @Autowired
    ServiceUsedRepository serviceUsedRepository;

    @Autowired
    CarRepairRepository carRepairRepository;

    @Override
    public ServiceUsedDTO createServiceUsed(ServiceUsedDTO serviceUsedDTO) {
        internalService.getServiceById(serviceUsedDTO.getServiceId());

        Optional<CarRepair> carRepair = carRepairRepository.findById(serviceUsedDTO.getCarRepairId());

        if(carRepair.isEmpty())
            throw AppError.ForeignKeyInvalid(CarRepair.class.getSimpleName());

        String uuid = GenerateUUID.generateRandomUUID();

        serviceUsedDTO.setId(uuid);

        ServiceUsed serviceUsed = new ServiceUsed(
                serviceUsedDTO.getId(),
                serviceUsedDTO.getAmount(),
                serviceUsedDTO.getServiceId(),
                carRepair.get()
        );

        serviceUsedRepository.save(serviceUsed);
        return serviceUsedDTO;
    }

    @Override
    public ServiceUsedDTO getServiceUsedById(String id) {
        Optional<ServiceUsed> serviceUsed = serviceUsedRepository.findById(id);

        if(serviceUsed.isEmpty())
            throw AppError.NotFoundError(ServiceUsed.class.getSimpleName());

        return ServiceUsedDTO.retrieveServiceUsed(serviceUsed.get());
    }

    @Override
    public void deleteServiceUsedById(String id) {
        Optional<ServiceUsed> serviceUsed = serviceUsedRepository.findById(id);
        if (serviceUsed.isEmpty())
            throw AppError.NotFoundError(ServiceUsed.class.getSimpleName());

        serviceUsedRepository.deleteById(id);
    }

    @Override
    public ServiceUsedDTO updateServiceUsedById(String id, Map<String, Object> fields) {
        Optional<ServiceUsed> serviceUsed = serviceUsedRepository.findById(id);
        if (serviceUsed.isEmpty())
            throw AppError.NotFoundError(ServiceUsed.class.getSimpleName());

        fields.forEach((key, value) -> {
            if (!key.equals("id")) {
                Field field = ReflectionUtils.findField(ServiceUsed.class, key);
                if (field != null) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, serviceUsed.get(), value);
                }
            }
        });

        internalService.getServiceById(serviceUsed.get().getServiceId());

//        String carRepairId = serviceUsed.get().getCarRepair().getId();
//
//        Optional<CarRepair> carRepair = carRepairRepository.findById(carRepairId);
//        if(carRepair.isEmpty())
//            throw AppError.BadRequestError("Car repair id is invalid: " + carRepairId);

        serviceUsedRepository.save(serviceUsed.get());

        return ServiceUsedDTO.retrieveServiceUsed(serviceUsed.get());
    }
}
