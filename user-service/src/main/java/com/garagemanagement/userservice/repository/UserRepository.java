package com.garagemanagement.userservice.repository;

import com.garagemanagement.userservice.common.entity.User;
import com.garagemanagement.userservice.common.model.RetrieveUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    Optional<User> findByPhone(String phone);

    @Query("SELECT new com.garagemanagement.userservice.common.model.RetrieveUserDTO(" +
            "u.id, u.username, u.fullname, u.phone, u.email, u.address, u.role) FROM User u" +
            " WHERE u.fullname LIKE LOWER(CONCAT('%', :fullname, '%')) AND u.role IN :role ORDER BY u.fullname ASC")
    Page<RetrieveUserDTO> findUsersByNameAndRole(String fullname, List<Integer> role, Pageable pageable);
}
