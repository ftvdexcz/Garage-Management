package com.garagemanagement.carrepairservice.repository;

import com.garagemanagement.carrepairservice.common.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, String> {
    Optional<Car> findByPlate(String plate);

    @Query("SELECT c FROM Car c WHERE c.customerId = :customerId")
    Page<Car> findCars(String customerId, Pageable pageable);
}
