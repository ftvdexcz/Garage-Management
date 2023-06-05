package com.garagemanagement.accessoryservice.controller;

import com.garagemanagement.accessoryservice.common.constant.StatusCodeResponse;
import com.garagemanagement.accessoryservice.common.entity.Supplier;
import com.garagemanagement.accessoryservice.common.handler.ResponseObject;
import com.garagemanagement.accessoryservice.common.model.CreateAccessoryDTO;
import com.garagemanagement.accessoryservice.common.model.CreateSupplierDTO;
import com.garagemanagement.accessoryservice.internal.InternalServiceImpl;
import com.garagemanagement.accessoryservice.service.SupplierService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    SupplierService supplierService;

    @Autowired
    InternalServiceImpl internalService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getSupplierById(@PathVariable String id){
        Supplier supplier = supplierService.getSupplierById(id);
        return ResponseEntity.ok(ResponseObject.successResponseWithData(supplier));
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> createSupplier(@RequestHeader("Authorization") String token,
                                                         @Valid @RequestBody CreateSupplierDTO createSupplierDTO){
        internalService.checkUserHasRole(token, "ADMIN");

        CreateSupplierDTO createdSupplier = supplierService.createSupplier(createSupplierDTO);
        return ResponseEntity.ok(ResponseObject.successResponseWithData(createdSupplier));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteSupplierById(@RequestHeader("Authorization") String token,
                                                             @PathVariable String id) {
        internalService.checkUserHasRole(token, "ADMIN");

        supplierService.deleteSupplierById(id);
        return ResponseEntity.status(StatusCodeResponse.STATUS_RESPONSE_DELETED_OK).
                body(ResponseObject.successResponseWithData(null));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseObject> updateSupplierById(@RequestHeader("Authorization") String token,
                                                             @PathVariable String id,
                                                            @RequestBody Map<String, Object> fields) {

        internalService.checkUserHasRole(token, "ADMIN");

        Supplier updatedSupplier = supplierService.updateSupplierById(id, fields);
        return ResponseEntity.status(StatusCodeResponse.STATUS_RESPONSE_CREATED_OK).
                body(ResponseObject.successResponseWithData(updatedSupplier));
    }
}
