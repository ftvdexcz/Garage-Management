package com.garagemanagement.accessoryservice.service;

import com.garagemanagement.accessoryservice.common.entity.Accessory;
import com.garagemanagement.accessoryservice.common.entity.Supplier;
import com.garagemanagement.accessoryservice.common.model.AccessoryPaginationDTO;
import com.garagemanagement.accessoryservice.common.model.CreateAccessoryDTO;
import com.garagemanagement.accessoryservice.common.model.PurchaseAccessoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface AccessoryService {
    CreateAccessoryDTO createAccessory(CreateAccessoryDTO createAccessoryDTO);

    CreateAccessoryDTO getAccessoryById(String id);

    void deleteAccessoryById(String id);

    CreateAccessoryDTO updateAccessoryById(String id, Map<String, Object> fields);

    PurchaseAccessoryDTO purchaseAccessory(String id, PurchaseAccessoryDTO purchaseAccessoryDTO);

    Page<AccessoryPaginationDTO> getAccessoriesByName(String name, Pageable pageable);
}
