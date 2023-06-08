package com.garagemanagement.accessoryservice.service;

import com.garagemanagement.accessoryservice.common.entity.ServiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface IService {
    ServiceEntity createService(ServiceEntity service);
    ServiceEntity getServiceById(String id);

    void deleteServiceById(String id);

    ServiceEntity updateServiceById(String id, Map<String, Object> fields);

    Page<ServiceEntity> getServicesByName(String name, Pageable pageable);
}
