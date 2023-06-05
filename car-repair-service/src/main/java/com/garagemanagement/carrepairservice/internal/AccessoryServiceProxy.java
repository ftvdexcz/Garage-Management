package com.garagemanagement.carrepairservice.internal;

import com.garagemanagement.carrepairservice.common.model.internal.AccessoryDTO;
import com.garagemanagement.carrepairservice.common.model.internal.ServiceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


//@FeignClient(name = "accessory-service", url = "http://localhost:8100")
@FeignClient(name = "accessory-service")
public interface AccessoryServiceProxy {
    @GetMapping("/internal/accessory/{id}")
    AccessoryDTO getAccessoryById(
            @PathVariable String id
    );

    @GetMapping("/internal/service/{id}")
    ServiceDTO getServiceById(
            @PathVariable String id
    );
}
