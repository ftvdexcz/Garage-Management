package com.garagemanagement.carrepairservice.service;

import com.garagemanagement.carrepairservice.common.entity.Bill;
import com.garagemanagement.carrepairservice.common.model.BillDTO;


import java.util.Map;

public interface BillService {
    Bill createBill(BillDTO billDTO);

    Bill getBillById(String id);

    void deleteBillById(String id);

    Bill updateBillById(String id, Map<String, Object> fields);
}
