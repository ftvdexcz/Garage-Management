package com.garagemanagement.accessoryservice.service;

import com.garagemanagement.accessoryservice.common.entity.ServiceEntity;

import java.util.Map;

public interface IService {
    ServiceEntity createService(ServiceEntity service);
    ServiceEntity getServiceById(String id);

    void deleteServiceById(String id);

    ServiceEntity updateServiceById(String id, Map<String, Object> fields);
}
