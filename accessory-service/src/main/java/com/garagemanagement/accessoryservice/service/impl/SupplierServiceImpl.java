package com.garagemanagement.accessoryservice.service.impl;

import com.garagemanagement.accessoryservice.common.entity.Accessory;
import com.garagemanagement.accessoryservice.common.entity.ServiceEntity;
import com.garagemanagement.accessoryservice.common.entity.Supplier;
import com.garagemanagement.accessoryservice.common.handler.AppError;
import com.garagemanagement.accessoryservice.common.model.CreateSupplierDTO;
import com.garagemanagement.accessoryservice.common.utils.GenerateUUID;
import com.garagemanagement.accessoryservice.repository.SupplierRepository;
import com.garagemanagement.accessoryservice.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    SupplierRepository supplierRepository;
    @Override
    public CreateSupplierDTO createSupplier(CreateSupplierDTO createSupplierDTO) {
        String id = GenerateUUID.generateRandomUUID();

        Optional<Supplier> supplier = supplierRepository.findByName(createSupplierDTO.getName());

        if (supplier.isPresent())
            throw AppError.ExistedNameError(Accessory.class.getSimpleName(), createSupplierDTO.getName());

        createSupplierDTO.setId(id);

        Supplier createdSupplier = supplierRepository.save(Supplier.createSupplier(createSupplierDTO));

        return createSupplierDTO;
    }

    public Supplier getSupplierById(String id){
        Optional<Supplier> supplier = supplierRepository.findById(id);

        if(supplier.isEmpty())
            throw AppError.NotFoundError(Supplier.class.getSimpleName());

        return supplier.get();
    }

    @Override
    public void deleteSupplierById(String id) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if (supplier.isEmpty())
            throw AppError.NotFoundError(Supplier.class.getSimpleName());

        supplierRepository.deleteById(id);
    }

    @Override
    public Supplier updateSupplierById(String id, Map<String, Object> fields) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if (supplier.isEmpty())
            throw AppError.NotFoundError(Supplier.class.getSimpleName());

        fields.forEach((key, value) -> {
            if (!key.equals("id")) {
                Field field = ReflectionUtils.findField(Supplier.class, key);
                if (field != null) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, supplier.get(), value);
                }
            }
        });

        return supplierRepository.save(supplier.get());
    }

    @Override
    public Page<Supplier> getSuppliersByName(String name, Pageable pageable) {
        return supplierRepository.findSuppliersByName(name, pageable);
    }
}
