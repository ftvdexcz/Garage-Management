package com.garagemanagement.carrepairservice.service;

import com.garagemanagement.carrepairservice.common.model.ServiceUsedDTO;

import java.util.Map;

public interface IServiceUsed {
    ServiceUsedDTO createServiceUsed(ServiceUsedDTO serviceUsedDTO);

    ServiceUsedDTO getServiceUsedById(String id);

    void deleteServiceUsedById(String id);

    ServiceUsedDTO updateServiceUsedById(String id, Map<String, Object> fields);
}
