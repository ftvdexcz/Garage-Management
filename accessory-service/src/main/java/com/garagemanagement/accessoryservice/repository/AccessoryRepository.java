package com.garagemanagement.accessoryservice.repository;

import com.garagemanagement.accessoryservice.common.entity.Accessory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccessoryRepository extends JpaRepository<Accessory, String> {
    Optional<Accessory> findByName(String name);
}
