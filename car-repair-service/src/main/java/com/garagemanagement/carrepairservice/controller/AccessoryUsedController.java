package com.garagemanagement.carrepairservice.controller;

import com.garagemanagement.carrepairservice.common.constant.StatusCodeResponse;
import com.garagemanagement.carrepairservice.common.handler.ResponseObject;
import com.garagemanagement.carrepairservice.common.model.AccessoryUsedDTO;
import com.garagemanagement.carrepairservice.internal.InternalServiceImpl;
import com.garagemanagement.carrepairservice.service.AccessoryUsedService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/accessory-used")
public class AccessoryUsedController {
    @Autowired
    AccessoryUsedService accessoryUsedService;

    @Autowired
    InternalServiceImpl internalService;

    @PostMapping("")
    public ResponseEntity<ResponseObject> createAccessoryUsed(@Valid @RequestBody AccessoryUsedDTO accessoryUsedDTO,
                                                              @RequestHeader("Authorization") String token
    ) {
        internalService.checkUserHasRole(token, "ADMIN,SUPPORT");

        AccessoryUsedDTO createdAccessoryUsed = accessoryUsedService.createAccessoryUsed(accessoryUsedDTO);
        return ResponseEntity.ok(ResponseObject.successResponseWithData(createdAccessoryUsed));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getAccessoryUsedById(@PathVariable String id) {
        AccessoryUsedDTO accessoryUsed = accessoryUsedService.getAccessoryUsedById(id);
        return ResponseEntity.ok(ResponseObject.successResponseWithData(accessoryUsed));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteAccessoryUsedById(@PathVariable String id, @RequestHeader("Authorization") String token
    ) {
        internalService.checkUserHasRole(token, "ADMIN,SUPPORT");

        accessoryUsedService.deleteAccessoryUsedById(id);
        return ResponseEntity.status(StatusCodeResponse.STATUS_RESPONSE_DELETED_OK).
                body(ResponseObject.successResponseWithData(null));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseObject> updateAccessoryUsedById(
            @PathVariable String id,
            @RequestBody Map<String, Object> fields,
            @RequestHeader("Authorization") String token) {
        internalService.checkUserHasRole(token, "ADMIN,SUPPORT");

        AccessoryUsedDTO updatedAccessoryUsed = accessoryUsedService.updateAccessoryUsedById(id, fields);
        return ResponseEntity.status(StatusCodeResponse.STATUS_RESPONSE_CREATED_OK).
                body(ResponseObject.successResponseWithData(updatedAccessoryUsed));
    }
}
