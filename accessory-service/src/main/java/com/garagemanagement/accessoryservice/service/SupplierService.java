package com.garagemanagement.accessoryservice.service;

import com.garagemanagement.accessoryservice.common.entity.Supplier;
import com.garagemanagement.accessoryservice.common.model.CreateSupplierDTO;

import java.util.Map;

public interface SupplierService {
    CreateSupplierDTO createSupplier(CreateSupplierDTO createSupplierDTO);
    Supplier getSupplierById(String id);

    void deleteSupplierById(String id);

    Supplier updateSupplierById(String id, Map<String, Object> fields);
}
