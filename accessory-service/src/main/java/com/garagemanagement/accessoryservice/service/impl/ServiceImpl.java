package com.garagemanagement.accessoryservice.service.impl;

import com.garagemanagement.accessoryservice.common.entity.Accessory;
import com.garagemanagement.accessoryservice.common.entity.ServiceEntity;
import com.garagemanagement.accessoryservice.common.entity.Supplier;
import com.garagemanagement.accessoryservice.common.handler.AppError;
import com.garagemanagement.accessoryservice.common.utils.GenerateUUID;
import com.garagemanagement.accessoryservice.repository.ServiceRepository;
import com.garagemanagement.accessoryservice.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Service
public class ServiceImpl implements IService {
    @Autowired
    ServiceRepository serviceRepository;

    @Override
    public ServiceEntity createService(ServiceEntity service) {
        String id = GenerateUUID.generateRandomUUID();

        Optional<ServiceEntity> serviceEntity = serviceRepository.findByName(service.getName());

        if (serviceEntity.isPresent())
            throw AppError.ExistedNameError(ServiceEntity.class.getSimpleName(), service.getName());

        service.setId(id);

        return serviceRepository.save(service);
    }

    @Override
    public ServiceEntity getServiceById(String id) {
        Optional<ServiceEntity> service = serviceRepository.findById(id);

        if(service.isEmpty())
            throw AppError.NotFoundError(ServiceEntity.class.getSimpleName());

        return service.get();
    }

    @Override
    public void deleteServiceById(String id) {
        Optional<ServiceEntity> service = serviceRepository.findById(id);
        if (service.isEmpty())
            throw AppError.NotFoundError(ServiceEntity.class.getSimpleName());

        serviceRepository.deleteById(id);
    }

    @Override
    public ServiceEntity updateServiceById(String id, Map<String, Object> fields) {
        Optional<ServiceEntity> service = serviceRepository.findById(id);
        if (service.isEmpty())
            throw AppError.NotFoundError(ServiceEntity.class.getSimpleName());

        fields.forEach((key, value) -> {
            if (!key.equals("id")) {
                Field field = ReflectionUtils.findField(ServiceEntity.class, key);
                if (field != null) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, service.get(), value);
                }
            }
        });

        return serviceRepository.save(service.get());
    }
}
