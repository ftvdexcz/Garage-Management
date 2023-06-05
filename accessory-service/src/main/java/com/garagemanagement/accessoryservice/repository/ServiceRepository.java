package com.garagemanagement.accessoryservice.repository;

import com.garagemanagement.accessoryservice.common.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceRepository extends JpaRepository<ServiceEntity, String> {
    Optional<ServiceEntity> findByName(String name);
}
