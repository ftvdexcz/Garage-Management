package com.garagemanagement.carrepairservice.service.impl;

import com.garagemanagement.carrepairservice.common.entity.AccessoryUsed;
import com.garagemanagement.carrepairservice.common.entity.Bill;
import com.garagemanagement.carrepairservice.common.entity.CarRepair;
import com.garagemanagement.carrepairservice.common.handler.AppError;
import com.garagemanagement.carrepairservice.common.model.AccessoryUsedDTO;
import com.garagemanagement.carrepairservice.common.model.BillDTO;
import com.garagemanagement.carrepairservice.common.utils.GenerateUUID;
import com.garagemanagement.carrepairservice.repository.BillRepository;
import com.garagemanagement.carrepairservice.repository.CarRepairRepository;
import com.garagemanagement.carrepairservice.repository.CarRepository;
import com.garagemanagement.carrepairservice.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    BillRepository billRepository;

    @Autowired
    CarRepairRepository carRepairRepository;

    @Autowired
    CarRepository carRepository;

    public Bill createPayment(BillDTO billDTO){
        Optional<CarRepair> carRepair = carRepairRepository.findById(billDTO.getCarRepairId());

        if(carRepair.isEmpty())
            throw AppError.ForeignKeyInvalid(CarRepair.class.getSimpleName());

        String uuid = GenerateUUID.generateRandomUUID();

        billDTO.setId(uuid);

        CarRepair _carRepair = carRepair.get();

        Bill bill = new Bill(
                billDTO.getId(),
                billDTO.getAmount(),
                billDTO.getPaymentDate(),
                _carRepair
        );

        _carRepair.setStatus(true);
        carRepairRepository.save(_carRepair);

        return billRepository.save(bill);
    }

    @Override
    public Bill createBill(BillDTO billDTO) {
        Optional<CarRepair> carRepair = carRepairRepository.findById(billDTO.getCarRepairId());

        if(carRepair.isEmpty())
            throw AppError.ForeignKeyInvalid(CarRepair.class.getSimpleName());

        String uuid = GenerateUUID.generateRandomUUID();

        billDTO.setId(uuid);

        Bill bill = new Bill(
                billDTO.getId(),
                billDTO.getAmount(),
                billDTO.getPaymentDate(),
                carRepair.get()
        );

        return billRepository.save(bill);
    }

    @Override
    public Bill getBillById(String id) {
        Optional<Bill> bill = billRepository.findById(id);

        if(bill.isEmpty())
            throw AppError.NotFoundError(Bill.class.getSimpleName());

        return bill.get();
    }

    public Bill getBillByRepairId(String id){
        Optional<Bill> bill = billRepository.findBillByRepairId(id);

        if(bill.isEmpty())
            throw AppError.NotFoundError(Bill.class.getSimpleName());

        return bill.get();
    }

    @Override
    public void deleteBillById(String id) {
        Optional<Bill> bill = billRepository.findById(id);

        if(bill.isEmpty())
            throw AppError.NotFoundError(Bill.class.getSimpleName());

        billRepository.deleteById(id);
    }

    @Override
    public Bill updateBillById(String id, Map<String, Object> fields) {
        Optional<Bill> bill = billRepository.findById(id);

        if(bill.isEmpty())
            throw AppError.NotFoundError(Bill.class.getSimpleName());

        fields.forEach((key, value) -> {
            if (!key.equals("id")) {
                Field field = ReflectionUtils.findField(Bill.class, key);
                if (field != null) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, bill.get(), value);
                }
            }
        });

//        String carRepairId = bill.get().getCarRepair().getId();
//        Optional<CarRepair> carRepair = carRepairRepository.findById(carRepairId);
//        if(carRepair.isEmpty())
//            throw AppError.BadRequestError("Car repair id is invalid: " + carRepairId);

        return billRepository.save(bill.get());
    }
}
