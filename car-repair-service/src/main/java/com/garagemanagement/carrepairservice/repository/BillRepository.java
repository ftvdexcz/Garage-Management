package com.garagemanagement.carrepairservice.repository;

import com.garagemanagement.carrepairservice.common.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, String> {
}
