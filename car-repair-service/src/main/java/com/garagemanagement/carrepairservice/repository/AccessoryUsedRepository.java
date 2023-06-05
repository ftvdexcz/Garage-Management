package com.garagemanagement.carrepairservice.repository;

import com.garagemanagement.carrepairservice.common.entity.AccessoryUsed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessoryUsedRepository extends JpaRepository<AccessoryUsed, String> {
}
