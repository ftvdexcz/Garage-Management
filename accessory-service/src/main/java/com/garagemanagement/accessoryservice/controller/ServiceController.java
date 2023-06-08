package com.garagemanagement.accessoryservice.controller;

import com.garagemanagement.accessoryservice.common.constant.StatusCodeResponse;
import com.garagemanagement.accessoryservice.common.entity.ServiceEntity;
import com.garagemanagement.accessoryservice.common.entity.Supplier;
import com.garagemanagement.accessoryservice.common.handler.ResponseObject;
import com.garagemanagement.accessoryservice.common.model.AccessoryPaginationDTO;
import com.garagemanagement.accessoryservice.common.model.CreateSupplierDTO;
import com.garagemanagement.accessoryservice.internal.InternalServiceImpl;
import com.garagemanagement.accessoryservice.service.IService;
import com.garagemanagement.accessoryservice.service.SupplierService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/service")
public class ServiceController {
    @Autowired
    IService iservice;

    @Autowired
    InternalServiceImpl internalService;

    @GetMapping("")
    public ResponseEntity<ResponseObject> getServicesPagination(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String name
    ){
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ServiceEntity> services = iservice.getServicesByName(name, pageable);

        return ResponseEntity.ok(ResponseObject.successResponseWithData(services));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getServiceById(@PathVariable String id){
        ServiceEntity service = iservice.getServiceById(id);
        return ResponseEntity.ok(ResponseObject.successResponseWithData(service));
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> createService(@RequestHeader("Authorization") String token,
                                                         @Valid @RequestBody ServiceEntity service){
        internalService.checkUserHasRole(token, "ADMIN");

        ServiceEntity createdService = iservice.createService(service);
        return ResponseEntity.ok(ResponseObject.successResponseWithData(createdService));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteServiceById(@RequestHeader("Authorization") String token,
                                                            @PathVariable String id) {
        internalService.checkUserHasRole(token, "ADMIN");

        iservice.deleteServiceById(id);
        return ResponseEntity.status(StatusCodeResponse.STATUS_RESPONSE_DELETED_OK).
                body(ResponseObject.successResponseWithData(null));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseObject> updateServiceById(@RequestHeader("Authorization") String token,
                                                            @PathVariable String id,
                                                            @RequestBody Map<String, Object> fields) {
        internalService.checkUserHasRole(token, "ADMIN");

        ServiceEntity updatedService = iservice.updateServiceById(id, fields);
        return ResponseEntity.status(StatusCodeResponse.STATUS_RESPONSE_CREATED_OK).
                body(ResponseObject.successResponseWithData(updatedService));
    }
}
