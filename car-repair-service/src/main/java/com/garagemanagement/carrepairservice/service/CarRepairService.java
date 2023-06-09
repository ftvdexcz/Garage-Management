package com.garagemanagement.carrepairservice.service;

import com.garagemanagement.carrepairservice.common.entity.Car;
import com.garagemanagement.carrepairservice.common.entity.CarRepair;
import com.garagemanagement.carrepairservice.common.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface CarRepairService {
    CarRepair createCarRepair(CarRepairDTO carRepairDTO);

    CarRepair getCarRepairById(String id);

    void deleteCarRepairById(String id);

    CarRepair updateCarRepairById(String id, Map<String, Object> fields);

    CarRepair createAppoinmentCarRepair(CreateAppointmentCarRepairDTO createAppointmentCarRepairDTO);

    CarRepair createDirectCarRepair(CreateDirectCarRepairDTO createDirectCarRepairDTO);

    SalaryEmployeeDTO getSalaryEmployeeById(SalaryEmployeeDTO salaryEmployeeDTO);


    List<SalaryEmployeeDTO> getSalaryEmployees(SalaryEmployeeDTO salaryEmployeeDTO);

    List<RevenueCustomerDTO> getRevenueCustomers(RevenueCustomerDTO revenueCustomerDTO);

    Page<CarRepairPaginationDTO> findCarRepairs(List<Boolean> status, String plate, Pageable pageable);

    Page<CarRepairPaginationDTO> findCarRepairsByCustomerId(List<Boolean> status, String customerId, Pageable pageable);
}
