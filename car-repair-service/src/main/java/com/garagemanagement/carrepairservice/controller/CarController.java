package com.garagemanagement.carrepairservice.controller;

import com.garagemanagement.carrepairservice.common.constant.StatusCodeResponse;
import com.garagemanagement.carrepairservice.common.entity.Car;
import com.garagemanagement.carrepairservice.common.handler.ResponseObject;
import com.garagemanagement.carrepairservice.internal.InternalServiceImpl;
import com.garagemanagement.carrepairservice.service.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/car")
public class CarController {
    @Autowired
    CarService carService;

    @Autowired
    InternalServiceImpl internalService;

    @GetMapping("")
    public ResponseEntity<ResponseObject> getServicesPagination(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String customerId
    ){
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Car> cars = carService.getAllCars(customerId, pageable);

        return ResponseEntity.ok(ResponseObject.successResponseWithData(cars));
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> createCar(@Valid @RequestBody Car car
    ) {

        Car createdCar = carService.createCar(car);
        return ResponseEntity.ok(ResponseObject.successResponseWithData(createdCar));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getCarById(@PathVariable String id) {
        Car car = carService.getCarById(id);
        return ResponseEntity.ok(ResponseObject.successResponseWithData(car));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteCarById(@PathVariable String id
    ) {
        carService.deleteCarById(id);
        return ResponseEntity.status(StatusCodeResponse.STATUS_RESPONSE_DELETED_OK).
                body(ResponseObject.successResponseWithData(null));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseObject> updateCarById(
            @PathVariable String id,
            @RequestBody Map<String, Object> fields) {

        Car updatedCar = carService.updateCarById(id, fields);
        return ResponseEntity.status(StatusCodeResponse.STATUS_RESPONSE_CREATED_OK).
                body(ResponseObject.successResponseWithData(updatedCar));
    }
}
