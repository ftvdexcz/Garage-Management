package com.garagemanagement.carrepairservice.repository;

import com.garagemanagement.carrepairservice.common.entity.CarRepair;
import com.garagemanagement.carrepairservice.common.model.Agg;
import com.garagemanagement.carrepairservice.common.model.CarRepairPaginationDTO;
import com.garagemanagement.carrepairservice.common.model.SalaryEmployeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface CarRepairRepository extends JpaRepository<CarRepair, String> {
    List<CarRepair> findByDateBetweenAndEmployeeId(Date startDate, Date endDate, String employeeId);

    @Query("SELECT new com.garagemanagement.carrepairservice.common.model.CarRepairPaginationDTO(" +
            "cr.id, cr.status, cr.car, cr.date, cr.employeeId, cr.note)" +
            " FROM CarRepair cr LEFT JOIN Car c" +
            " ON cr.car.id = c.id WHERE cr.status IN :status AND c.plate LIKE LOWER(CONCAT('%', :plate, '%')) ORDER BY cr.date DESC")
    Page<CarRepairPaginationDTO> findCarRepairs(List<Boolean> status, String plate, Pageable pageable);

    @Query("SELECT new com.garagemanagement.carrepairservice.common.model.CarRepairPaginationDTO(" +
            "cr.id, cr.status, cr.car, cr.date, cr.employeeId, cr.note)" +
            " FROM CarRepair cr LEFT JOIN Car c" +
            " ON cr.car.id = c.id WHERE cr.status IN :status AND c.customerId = :customerId ORDER BY cr.date DESC")
    Page<CarRepairPaginationDTO> findCarRepairsByCustomerId(List<Boolean> status, String customerId, Pageable pageable);

    @Query("SELECT new com.garagemanagement.carrepairservice.common.model.Agg(cr.id, count(cr))" +
            " FROM CarRepair cr LEFT JOIN Car c" +
            " ON cr.car.id = c.id WHERE cr.status = true AND c.customerId = :customerId " +
            " AND cr.date <= :end AND cr.date >= :start  ORDER BY cr.date DESC")
    List<Agg> findCarRepairsByCustomerIdBetweenDate(Date start, Date end, String customerId);
}
