package com.garagemanagement.accessoryservice.service;

import com.garagemanagement.accessoryservice.common.entity.ServiceEntity;
import com.garagemanagement.accessoryservice.common.entity.Supplier;
import com.garagemanagement.accessoryservice.common.model.AccessoryPaginationDTO;
import com.garagemanagement.accessoryservice.common.model.CreateSupplierDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface SupplierService {
    CreateSupplierDTO createSupplier(CreateSupplierDTO createSupplierDTO);
    Supplier getSupplierById(String id);

    void deleteSupplierById(String id);

    Supplier updateSupplierById(String id, Map<String, Object> fields);

    Page<Supplier> getSuppliersByName(String name, Pageable pageable);

}
