package com.garagemanagement.userservice.service.impl;

import com.garagemanagement.userservice.common.entity.User;
import com.garagemanagement.userservice.common.handler.AppError;
import com.garagemanagement.userservice.common.model.CreateUserDTO;
import com.garagemanagement.userservice.common.model.RetrieveUserDTO;
import com.garagemanagement.userservice.common.utils.GenerateUUID;
import com.garagemanagement.userservice.common.utils.HashedPassword;
import com.garagemanagement.userservice.repository.UserRepository;
import com.garagemanagement.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public RetrieveUserDTO getUserById(String id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty())
            throw AppError.NotFoundError(User.class.getSimpleName());

        return RetrieveUserDTO.retrieveUser(user.get());
    }


    @Override
    public RetrieveUserDTO createUser(CreateUserDTO createUserDTO) {
        String id = GenerateUUID.generateRandomUUID();

        Optional<User> user = userRepository.findByUsername(createUserDTO.getUsername());

        if (user.isPresent())
            throw AppError.ExistedNameError(User.class.getSimpleName(), createUserDTO.getUsername());

        createUserDTO.setId(id);

        // encode password
        createUserDTO.setPassword(HashedPassword.encryptPassword(createUserDTO.getPassword()));

        User savedUser = userRepository.save(User.createUser(createUserDTO));

        return RetrieveUserDTO.retrieveUser(savedUser);
    }

    public void deleteUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw AppError.NotFoundError(User.class.getSimpleName());

        userRepository.deleteById(id);
    }

    public RetrieveUserDTO updateUserById(String id, Map<String, Object> fields, boolean isAdmin) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw AppError.NotFoundError(User.class.getSimpleName());

        fields.forEach((key, value) -> {
            if (!key.equals("id") && !(key.equals("role") && !isAdmin)) {
                Field field = ReflectionUtils.findField(User.class, key);
                if (field != null) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, user.get(), value);
                }
            }
        });

        User updatedUser = userRepository.save(user.get());
        return RetrieveUserDTO.retrieveUser(updatedUser);
    }
}
