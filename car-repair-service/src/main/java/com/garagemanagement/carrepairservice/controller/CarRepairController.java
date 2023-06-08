package com.garagemanagement.carrepairservice.controller;

import com.garagemanagement.carrepairservice.common.constant.StatusCodeResponse;
import com.garagemanagement.carrepairservice.common.entity.Car;
import com.garagemanagement.carrepairservice.common.entity.CarRepair;
import com.garagemanagement.carrepairservice.common.handler.ResponseObject;
import com.garagemanagement.carrepairservice.common.model.CarRepairDTO;
import com.garagemanagement.carrepairservice.common.model.CarRepairPaginationDTO;
import com.garagemanagement.carrepairservice.common.model.CreateAppointmentCarRepairDTO;
import com.garagemanagement.carrepairservice.common.model.CreateDirectCarRepairDTO;
import com.garagemanagement.carrepairservice.internal.InternalServiceImpl;
import com.garagemanagement.carrepairservice.service.CarRepairService;
import com.garagemanagement.carrepairservice.service.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/car-repair")
public class CarRepairController {
    @Autowired
    CarRepairService carRepairService;

    @Autowired
    InternalServiceImpl internalService;

    @GetMapping("")
    public ResponseEntity<ResponseObject> getCarRepairsPagination(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String status,
            @RequestParam(defaultValue = "") String plate
    ){
        Pageable pageable = PageRequest.of(page - 1, size);

        List<Boolean> s = new ArrayList<>();
        for(String i: status.split(",")){
            s.add(Boolean.valueOf(i));
        };

        System.out.println(s);
        Page<CarRepairPaginationDTO> carRepairPaginationDTOPage = carRepairService.findCarRepairs(s, plate, pageable);

        return ResponseEntity.ok(ResponseObject.successResponseWithData(carRepairPaginationDTOPage));
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> createCarRepair(@Valid @RequestBody CarRepairDTO carRepairDTO,
                                                    @RequestHeader("Authorization") String token
    ) {
        internalService.checkUserHasRole(token, "ADMIN,SUPPORT");

        CarRepair createdCarRepair = carRepairService.createCarRepair(carRepairDTO);
        return ResponseEntity.ok(ResponseObject.successResponseWithData(createdCarRepair));
    }

    @PostMapping("/appointment")
    public ResponseEntity<ResponseObject> createAppointmentCarRepair(
            @Valid @RequestBody CreateAppointmentCarRepairDTO createAppointmentCarRepairDTO
    ){
        CarRepair createdAppoinmentCarRepair = carRepairService.createAppoinmentCarRepair(createAppointmentCarRepairDTO);
        return ResponseEntity.ok(ResponseObject.successResponseWithData(createdAppoinmentCarRepair));
    }

    @PostMapping("/direct")
    public ResponseEntity<ResponseObject> createDirectCarRepair(
            @Valid @RequestBody CreateDirectCarRepairDTO createDirectCarRepairDTO
    ){
        CarRepair creadDirectCarRepair = carRepairService.createDirectCarRepair(createDirectCarRepairDTO);
        return ResponseEntity.ok(ResponseObject.successResponseWithData(creadDirectCarRepair));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getCarRepairById(@PathVariable String id) {
        CarRepair carRepair = carRepairService.getCarRepairById(id);
        return ResponseEntity.ok(ResponseObject.successResponseWithData(carRepair));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteCarRepairById(@PathVariable String id,
                                                              @RequestHeader("Authorization") String token
    ) {
        internalService.checkUserHasRole(token, "ADMIN,SUPPORT");

        carRepairService.deleteCarRepairById(id);
        return ResponseEntity.status(StatusCodeResponse.STATUS_RESPONSE_DELETED_OK).
                body(ResponseObject.successResponseWithData(null));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseObject> updateCarRepairById(
            @PathVariable String id,
            @RequestBody Map<String, Object> fields,
            @RequestHeader("Authorization") String token) {
        internalService.checkUserHasRole(token, "ADMIN,SUPPORT");

        CarRepair updatedCarRepair = carRepairService.updateCarRepairById(id, fields);
        return ResponseEntity.status(StatusCodeResponse.STATUS_RESPONSE_CREATED_OK).
                body(ResponseObject.successResponseWithData(updatedCarRepair));
    }

}
