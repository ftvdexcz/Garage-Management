package com.garagemanagement.accessoryservice.controller;

import com.garagemanagement.accessoryservice.common.constant.StatusCodeResponse;
import com.garagemanagement.accessoryservice.common.entity.Accessory;
import com.garagemanagement.accessoryservice.common.entity.Supplier;
import com.garagemanagement.accessoryservice.common.handler.ResponseObject;
import com.garagemanagement.accessoryservice.common.model.AccessoryPaginationDTO;
import com.garagemanagement.accessoryservice.common.model.CreateAccessoryDTO;
import com.garagemanagement.accessoryservice.common.model.CreateSupplierDTO;
import com.garagemanagement.accessoryservice.common.model.PurchaseAccessoryDTO;
import com.garagemanagement.accessoryservice.internal.InternalServiceImpl;
import com.garagemanagement.accessoryservice.service.AccessoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/accessory")
public class AccessoryController {
    @Autowired
    AccessoryService accessoryService;

    @Autowired
    InternalServiceImpl internalService;

    @GetMapping("")
    public ResponseEntity<ResponseObject> getAccessoriesPagination(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String name
    ){
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<AccessoryPaginationDTO> accessories = accessoryService.getAccessoriesByName(name, pageable);

        return ResponseEntity.ok(ResponseObject.successResponseWithData(accessories));
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> createAccessory(@Valid @RequestBody CreateAccessoryDTO createAccessoryDTO,
                                                          @RequestHeader("Authorization") String token) {
        System.out.println(token);
        internalService.checkUserHasRole(token, "ADMIN");

        CreateAccessoryDTO createdAccessory = accessoryService.createAccessory(createAccessoryDTO);
        return ResponseEntity.ok(ResponseObject.successResponseWithData(createdAccessory));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getAccessoryById(@PathVariable String id) {
        CreateAccessoryDTO accessory = accessoryService.getAccessoryById(id);
        return ResponseEntity.ok(ResponseObject.successResponseWithData(accessory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteAccessoryById(@PathVariable String id,
                                                              @RequestHeader("Authorization") String token) {
        internalService.checkUserHasRole(token, "ADMIN");

        accessoryService.deleteAccessoryById(id);
        return ResponseEntity.status(StatusCodeResponse.STATUS_RESPONSE_DELETED_OK).
                body(ResponseObject.successResponseWithData(null));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseObject> updateAccessoryById(@RequestHeader("Authorization") String token,
                                                              @PathVariable String id,
                                                              @RequestBody Map<String, Object> fields) {
        internalService.checkUserHasRole(token, "ADMIN");
        CreateAccessoryDTO updatedAccessory = accessoryService.updateAccessoryById(id, fields);
        return ResponseEntity.status(StatusCodeResponse.STATUS_RESPONSE_CREATED_OK).
                body(ResponseObject.successResponseWithData(updatedAccessory));
    }

    @PostMapping("/{id}/purchase")
    public ResponseEntity<ResponseObject> purchaseAccessory(
            @PathVariable String id,
            @RequestBody PurchaseAccessoryDTO purchaseAccessoryDTO
    ) {
        PurchaseAccessoryDTO savedPurchaseAccessory = accessoryService.purchaseAccessory(id, purchaseAccessoryDTO);
        return ResponseEntity.ok(ResponseObject.successResponseWithData(savedPurchaseAccessory));
    }
}
