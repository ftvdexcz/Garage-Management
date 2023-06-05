package com.garagemanagement.carrepairservice.repository;

import com.garagemanagement.carrepairservice.common.entity.CarRepair;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepairRepository extends JpaRepository<CarRepair, String> {
}
