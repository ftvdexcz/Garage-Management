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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

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
    public List<RetrieveUserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        List<RetrieveUserDTO> usersDTO = new ArrayList<>();
        for (User u: users){
            usersDTO.add(RetrieveUserDTO.retrieveUser(u));
        }
        return usersDTO;
    }


    @Override
    public RetrieveUserDTO createUser(CreateUserDTO createUserDTO) {
        String id = GenerateUUID.generateRandomUUID();

        Optional<User> user = userRepository.findByUsername(createUserDTO.getUsername());

        if (user.isPresent())
            throw AppError.ExistedNameError(User.class.getSimpleName(), createUserDTO.getUsername());

        Optional<User> user2 = userRepository.findByPhone(createUserDTO.getPhone());

        if (user2.isPresent())
            throw AppError.ExistedNameError(User.class.getSimpleName(), createUserDTO.getPhone());

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

    public RetrieveUserDTO getUserByPhone(String phone){
        Optional<User> user = userRepository.findByPhone(phone);

        if (user.isEmpty())
            throw AppError.NotFoundError(User.class.getSimpleName());

        return RetrieveUserDTO.retrieveUser(user.get());
    }

    @Override
    public Page<RetrieveUserDTO> getUsersByNameAndRole(String name, String role, Pageable pageable) {
        List<Integer> stringList = new ArrayList<>();
        String[] r = role.split(",");

        for(String s: r){
            stringList.add(Integer.parseInt(s));
        }
        return userRepository.findUsersByNameAndRole(name, stringList, pageable);
    }
}
