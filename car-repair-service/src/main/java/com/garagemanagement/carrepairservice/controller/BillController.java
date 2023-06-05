package com.garagemanagement.carrepairservice.controller;

import com.garagemanagement.carrepairservice.common.constant.StatusCodeResponse;
import com.garagemanagement.carrepairservice.common.entity.Bill;
import com.garagemanagement.carrepairservice.common.handler.ResponseObject;

import com.garagemanagement.carrepairservice.common.model.BillDTO;
import com.garagemanagement.carrepairservice.internal.InternalServiceImpl;
import com.garagemanagement.carrepairservice.service.BillService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/bill")
public class BillController {
    @Autowired
    BillService billService;

    @Autowired
    InternalServiceImpl internalService;

    @PostMapping("")
    public ResponseEntity<ResponseObject> createBill(@Valid @RequestBody BillDTO billDTO,
                                                              @RequestHeader("Authorization") String token
    ) {
        internalService.checkUserHasRole(token, "ADMIN,SUPPORT");

        Bill createdBillDTO = billService.createBill(billDTO);
        return ResponseEntity.ok(ResponseObject.successResponseWithData(createdBillDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getBillById(@PathVariable String id) {
        Bill bill = billService.getBillById(id);
        return ResponseEntity.ok(ResponseObject.successResponseWithData(bill));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteBillById(@PathVariable String id, @RequestHeader("Authorization") String token
    ) {
        internalService.checkUserHasRole(token, "ADMIN,SUPPORT");

        billService.deleteBillById(id);
        return ResponseEntity.status(StatusCodeResponse.STATUS_RESPONSE_DELETED_OK).
                body(ResponseObject.successResponseWithData(null));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseObject> updateBillById(
            @PathVariable String id,
            @RequestBody Map<String, Object> fields,
            @RequestHeader("Authorization") String token) {
        internalService.checkUserHasRole(token, "ADMIN,SUPPORT");

        Bill updatedBill = billService.updateBillById(id, fields);
        return ResponseEntity.status(StatusCodeResponse.STATUS_RESPONSE_CREATED_OK).
                body(ResponseObject.successResponseWithData(updatedBill));
    }
}
