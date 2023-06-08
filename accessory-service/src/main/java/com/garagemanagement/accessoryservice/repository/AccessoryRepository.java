package com.garagemanagement.accessoryservice.repository;

import com.garagemanagement.accessoryservice.common.entity.Accessory;
import com.garagemanagement.accessoryservice.common.model.AccessoryPaginationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccessoryRepository extends JpaRepository<Accessory, String> {
    Optional<Accessory> findByName(String name);

    @Query("SELECT new com.garagemanagement.accessoryservice.common.model.AccessoryPaginationDTO(a, s.name, s.id) FROM Accessory a LEFT JOIN Supplier s" +
            " ON a.supplier.id = s.id WHERE a.name LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<AccessoryPaginationDTO> findAccessoriesByName(String name, Pageable pageable);
}
