package com.garagemanagement.accessoryservice.repository;

import com.garagemanagement.accessoryservice.common.entity.ServiceEntity;
import com.garagemanagement.accessoryservice.common.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, String> {
    Optional<Supplier> findByName(String name);

    @Query("SELECT s FROM Supplier s WHERE s.name LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Supplier> findSuppliersByName(String name, Pageable pageable);
}
