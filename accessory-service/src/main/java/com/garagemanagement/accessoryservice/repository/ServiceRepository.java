package com.garagemanagement.accessoryservice.repository;

import com.garagemanagement.accessoryservice.common.entity.ServiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ServiceRepository extends JpaRepository<ServiceEntity, String> {
    Optional<ServiceEntity> findByName(String name);

    @Query("SELECT s FROM ServiceEntity s WHERE s.name LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<ServiceEntity> findServicesByName(String name, Pageable pageable);
}
