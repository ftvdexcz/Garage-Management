package com.garagemanagement.carrepairservice.repository;

import com.garagemanagement.carrepairservice.common.entity.ServiceUsed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceUsedRepository extends JpaRepository<ServiceUsed, String> {
}
