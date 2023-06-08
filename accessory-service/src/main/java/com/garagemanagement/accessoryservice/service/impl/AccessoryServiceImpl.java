package com.garagemanagement.accessoryservice.service.impl;

import com.garagemanagement.accessoryservice.common.entity.Accessory;
import com.garagemanagement.accessoryservice.common.entity.AccessoryPurchased;
import com.garagemanagement.accessoryservice.common.entity.Supplier;
import com.garagemanagement.accessoryservice.common.handler.AppError;
import com.garagemanagement.accessoryservice.common.model.AccessoryPaginationDTO;
import com.garagemanagement.accessoryservice.common.model.CreateAccessoryDTO;
import com.garagemanagement.accessoryservice.common.model.PurchaseAccessoryDTO;
import com.garagemanagement.accessoryservice.common.model.internal.RetrieveUserDTO;
import com.garagemanagement.accessoryservice.common.utils.GenerateUUID;
import com.garagemanagement.accessoryservice.internal.InternalServiceImpl;
import com.garagemanagement.accessoryservice.internal.UserServiceProxy;
import com.garagemanagement.accessoryservice.repository.AccessoryPurchasedRepository;
import com.garagemanagement.accessoryservice.repository.AccessoryRepository;
import com.garagemanagement.accessoryservice.repository.SupplierRepository;
import com.garagemanagement.accessoryservice.service.AccessoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Service
public class AccessoryServiceImpl implements AccessoryService {
    @Autowired
    AccessoryRepository accessoryRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    UserServiceProxy userServiceProxy;

    @Autowired
    InternalServiceImpl internalService;

    @Autowired
    AccessoryPurchasedRepository accessoryPurchasedRepository;

    public Page<AccessoryPaginationDTO> getAccessoriesByName(String name, Pageable pageable){
        return accessoryRepository.findAccessoriesByName(name, pageable);
    }

    public CreateAccessoryDTO createAccessory(CreateAccessoryDTO createAccessoryDTO){
        String id = GenerateUUID.generateRandomUUID();

        Optional<Accessory> accessory = accessoryRepository.findByName(createAccessoryDTO.getName());

        if (accessory.isPresent())
            throw AppError.ExistedNameError(Accessory.class.getSimpleName(), createAccessoryDTO.getName());

        Optional<Supplier> supplier = supplierRepository.findById(createAccessoryDTO.getSupplierId());

        if(supplier.isEmpty())
            throw AppError.ForeignKeyInvalid(Supplier.class.getSimpleName());

        createAccessoryDTO.setId(id);

        Accessory createAccessory = new Accessory(
                createAccessoryDTO.getId(),
                createAccessoryDTO.getName(),
                createAccessoryDTO.getPrice(),
                createAccessoryDTO.getQuantity(),
                createAccessoryDTO.getImageSource(),
                supplier.get()
        );

        Accessory savedAccessory = accessoryRepository.save(createAccessory);

        return createAccessoryDTO;
    }

    public CreateAccessoryDTO getAccessoryById(String id){
        Optional<Accessory> accessory = accessoryRepository.findById(id);

        if(accessory.isEmpty())
            throw AppError.NotFoundError(Accessory.class.getSimpleName());

        return CreateAccessoryDTO.retrieveAccessory(accessory.get());
    }

    @Override
    public void deleteAccessoryById(String id) {
        Optional<Accessory> accessory = accessoryRepository.findById(id);
        if (accessory.isEmpty())
            throw AppError.NotFoundError(Supplier.class.getSimpleName());

        accessoryRepository.deleteById(id);
    }

    @Override
    public CreateAccessoryDTO updateAccessoryById(String id, Map<String, Object> fields) {
        Optional<Accessory> accessory = accessoryRepository.findById(id);
        if (accessory.isEmpty())
            throw AppError.NotFoundError(Accessory.class.getSimpleName());

        fields.forEach((key, value) -> {
            if (!key.equals("id")) {
                Field field = ReflectionUtils.findField(Accessory.class, key);
                if (field != null) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, accessory.get(), value);
                }
            }
        });

        accessoryRepository.save(accessory.get());
        return CreateAccessoryDTO.retrieveAccessory(accessory.get());
    }

    @Override
    public PurchaseAccessoryDTO purchaseAccessory(String id, PurchaseAccessoryDTO purchaseAccessoryDTO) {
        Optional<Accessory> accessory = accessoryRepository.findById(id);
        if (accessory.isEmpty())
            throw AppError.NotFoundError(Accessory.class.getSimpleName());

        internalService.checkUserRoleById(purchaseAccessoryDTO.getEmployeeId(), "ADMIN,SUPPORT");

        String uuid = GenerateUUID.generateRandomUUID();

        purchaseAccessoryDTO.setId(uuid);

        AccessoryPurchased accessoryPurchased =  new AccessoryPurchased(
                purchaseAccessoryDTO.getId(),
                purchaseAccessoryDTO.getQuantity(),
                purchaseAccessoryDTO.getAmount(),
                purchaseAccessoryDTO.getPurchasedDate(),
                purchaseAccessoryDTO.getEmployeeId(),
                accessory.get()
        );

        accessoryPurchasedRepository.save(accessoryPurchased);

        accessory.get().setQuantity(accessory.get().getQuantity() - purchaseAccessoryDTO.getQuantity());

        accessoryRepository.save(accessory.get());

        return purchaseAccessoryDTO;
    }
}
