package com.garagemanagement.carrepairservice.controller;

import com.garagemanagement.carrepairservice.common.handler.ResponseObject;
import com.garagemanagement.carrepairservice.common.model.SalaryEmployeeDTO;
import com.garagemanagement.carrepairservice.internal.InternalServiceImpl;
import com.garagemanagement.carrepairservice.service.CarRepairService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salary")
public class SalaryController {
    @Autowired
    CarRepairService carRepairService;

    @Autowired
    InternalServiceImpl internalService;

    @PostMapping("")
    public ResponseEntity<ResponseObject> getSalaryEmployees(@Valid @RequestBody SalaryEmployeeDTO salaryEmployeeDTO,
                                                             @RequestHeader("Authorization") String token){
        internalService.checkUserHasRole(token, "ADMIN");

        List<SalaryEmployeeDTO> s = carRepairService.getSalaryEmployees(salaryEmployeeDTO);
        return ResponseEntity.ok(ResponseObject.successResponseWithData(s));
    }

    @PostMapping("/{id}")
    public ResponseEntity<ResponseObject> getSalaryEmployeeById(@PathVariable String id,
                                                                @Valid @RequestBody SalaryEmployeeDTO salaryEmployeeDTO,
                                                                @RequestHeader("Authorization") String token) {
        internalService.checkUserHasRole(token, "ADMIN");

        salaryEmployeeDTO.setEmployeeId(id);
        SalaryEmployeeDTO s = carRepairService.getSalaryEmployeeById(salaryEmployeeDTO);
        return ResponseEntity.ok(ResponseObject.successResponseWithData(s));
    }


}
