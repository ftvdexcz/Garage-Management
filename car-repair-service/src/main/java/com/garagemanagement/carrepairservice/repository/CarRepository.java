package com.garagemanagement.carrepairservice.repository;

import com.garagemanagement.carrepairservice.common.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, String> {
    Optional<Car> findByPlate(String plate);
}
