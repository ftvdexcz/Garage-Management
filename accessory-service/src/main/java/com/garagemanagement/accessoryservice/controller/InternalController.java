package com.garagemanagement.accessoryservice.controller;

import com.garagemanagement.accessoryservice.common.entity.Accessory;
import com.garagemanagement.accessoryservice.common.entity.ServiceEntity;
import com.garagemanagement.accessoryservice.common.handler.ResponseObject;
import com.garagemanagement.accessoryservice.common.model.CreateAccessoryDTO;
import com.garagemanagement.accessoryservice.service.AccessoryService;
import com.garagemanagement.accessoryservice.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal")
public class InternalController {
    @Autowired
    AccessoryService accessoryService;

    @Autowired
    IService iService;

    @GetMapping("/accessory/{id}")
    public CreateAccessoryDTO getAccessoryById(@PathVariable String id) {
        return accessoryService.getAccessoryById(id);
    }

    @GetMapping("/service/{id}")
    public ServiceEntity getServiceById(@PathVariable String id){
        return iService.getServiceById(id);
    }
}
