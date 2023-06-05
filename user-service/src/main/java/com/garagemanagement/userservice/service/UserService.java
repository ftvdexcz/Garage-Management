package com.garagemanagement.userservice.service;

import com.garagemanagement.userservice.common.model.CreateUserDTO;
import com.garagemanagement.userservice.common.model.RetrieveUserDTO;

import java.util.Map;

public interface UserService {
    RetrieveUserDTO getUserById(String id);
    RetrieveUserDTO createUser(CreateUserDTO createUserDTO);
    void deleteUserById(String id);

    RetrieveUserDTO updateUserById(String id, Map<String, Object> fields, boolean isAdmin);
}
