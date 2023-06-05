package com.garagemanagement.accessoryservice.repository;

import com.garagemanagement.accessoryservice.common.entity.AccessoryPurchased;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessoryPurchasedRepository extends JpaRepository<AccessoryPurchased, String> {
}
