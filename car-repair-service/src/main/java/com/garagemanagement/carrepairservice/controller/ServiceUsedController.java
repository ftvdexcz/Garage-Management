package com.garagemanagement.carrepairservice.controller;

import com.garagemanagement.carrepairservice.common.constant.StatusCodeResponse;
import com.garagemanagement.carrepairservice.common.handler.ResponseObject;
import com.garagemanagement.carrepairservice.common.model.AccessoryUsedDTO;
import com.garagemanagement.carrepairservice.common.model.ServiceUsedDTO;
import com.garagemanagement.carrepairservice.internal.InternalServiceImpl;
import com.garagemanagement.carrepairservice.service.AccessoryUsedService;
import com.garagemanagement.carrepairservice.service.IServiceUsed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/service-used")
public class ServiceUsedController {
    @Autowired
    IServiceUsed iServiceUsed;

    @Autowired
    InternalServiceImpl internalService;

    @PostMapping("")
    public ResponseEntity<ResponseObject> createServiceUsed(@Valid @RequestBody ServiceUsedDTO serviceUsedDTO,
                                                              @RequestHeader("Authorization") String token
    ) {
        internalService.checkUserHasRole(token, "ADMIN,SUPPORT");

        ServiceUsedDTO createdServiceUsed = iServiceUsed.createServiceUsed(serviceUsedDTO);
        return ResponseEntity.ok(ResponseObject.successResponseWithData(createdServiceUsed));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getServiceUsedById(@PathVariable String id) {
        ServiceUsedDTO serviceUsedDTO = iServiceUsed.getServiceUsedById(id);
        return ResponseEntity.ok(ResponseObject.successResponseWithData(serviceUsedDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteServiceUsedById(@PathVariable String id, @RequestHeader("Authorization") String token
    ) {
        internalService.checkUserHasRole(token, "ADMIN,SUPPORT");

        iServiceUsed.deleteServiceUsedById(id);
        return ResponseEntity.status(StatusCodeResponse.STATUS_RESPONSE_DELETED_OK).
                body(ResponseObject.successResponseWithData(null));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseObject> updateServiceUsedById(
            @PathVariable String id,
            @RequestBody Map<String, Object> fields,
            @RequestHeader("Authorization") String token) {
        internalService.checkUserHasRole(token, "ADMIN,SUPPORT");

        ServiceUsedDTO updatedServiceUsed = iServiceUsed.updateServiceUsedById(id, fields);
        return ResponseEntity.status(StatusCodeResponse.STATUS_RESPONSE_CREATED_OK).
                body(ResponseObject.successResponseWithData(updatedServiceUsed));
    }
}
