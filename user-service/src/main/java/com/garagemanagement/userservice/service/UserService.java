package com.garagemanagement.userservice.service;

import com.garagemanagement.userservice.common.model.CreateUserDTO;
import com.garagemanagement.userservice.common.model.RetrieveUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface UserService {
    RetrieveUserDTO getUserById(String id);

    RetrieveUserDTO getUserByPhone(String phone);
    RetrieveUserDTO createUser(CreateUserDTO createUserDTO);
    void deleteUserById(String id);

    RetrieveUserDTO updateUserById(String id, Map<String, Object> fields, boolean isAdmin);

    Page<RetrieveUserDTO> getUsersByNameAndRole(String name, String role, Pageable pageable);

    List<RetrieveUserDTO> getAllUsers();
}
