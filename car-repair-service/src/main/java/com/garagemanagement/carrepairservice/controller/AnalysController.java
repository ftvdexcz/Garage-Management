package com.garagemanagement.carrepairservice.controller;


import com.garagemanagement.carrepairservice.common.handler.ResponseObject;
import com.garagemanagement.carrepairservice.common.model.RevenueCustomerDTO;
import com.garagemanagement.carrepairservice.common.model.SalaryEmployeeDTO;
import com.garagemanagement.carrepairservice.internal.InternalServiceImpl;
import com.garagemanagement.carrepairservice.service.CarRepairService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/analys")
public class AnalysController {
    @Autowired
    CarRepairService carRepairService;

    @Autowired
    InternalServiceImpl internalService;

    @PostMapping("/customer")
    public ResponseEntity<ResponseObject> getRevenueCustomers(@Valid @RequestBody RevenueCustomerDTO revenueCustomerDTO,
                                                             @RequestHeader("Authorization") String token){
        internalService.checkUserHasRole(token, "ADMIN");

        List<RevenueCustomerDTO> s = carRepairService.getRevenueCustomers(revenueCustomerDTO);
        return ResponseEntity.ok(ResponseObject.successResponseWithData(s));
    }
}