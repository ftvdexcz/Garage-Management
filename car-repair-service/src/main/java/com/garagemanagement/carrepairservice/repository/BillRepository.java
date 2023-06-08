package com.garagemanagement.carrepairservice.repository;

import com.garagemanagement.carrepairservice.common.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, String> {
    @Query("SELECT b FROM Bill b LEFT JOIN CarRepair c ON c.id = b.carRepair.id WHERE c.id = :id")
    Optional<Bill> findBillByRepairId(String id);
}
