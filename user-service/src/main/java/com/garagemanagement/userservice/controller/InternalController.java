package com.garagemanagement.userservice.controller;

import com.garagemanagement.userservice.common.model.CreateUserDTO;
import com.garagemanagement.userservice.common.model.RetrieveUserDTO;
import com.garagemanagement.userservice.service.AuthService;
import com.garagemanagement.userservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal")
public class InternalController {
    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @PostMapping("/user")
    public ResponseEntity<RetrieveUserDTO> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        try{
            RetrieveUserDTO createdUserDTO = userService.createUser(createUserDTO);
            return ResponseEntity.ok(createdUserDTO);
        }catch (Exception e){
            return null;
        }
    }

    @GetMapping("/user/phone/{phone}")
    public ResponseEntity<RetrieveUserDTO> getUserByPhone(@PathVariable String phone) {
        try{
            RetrieveUserDTO user = userService.getUserByPhone(phone);
            return ResponseEntity.ok(user);
        }catch(Exception e){
            return null;
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<RetrieveUserDTO> getUserById(@PathVariable String id) {
        try{
            RetrieveUserDTO user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        }catch (Exception e){
            return null;
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<RetrieveUserDTO>> getAllUsers() {
        try{
            List<RetrieveUserDTO> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        }catch (Exception e){
            return null;
        }
    }


    @PostMapping("/auth/check-role")
    public ResponseEntity<Boolean> checkRole(HttpServletRequest request,
                                                    @RequestHeader("x-user-role") String roles ) {
        boolean hasRole;
        try{
            hasRole = authService.checkRole(request, roles);
        }catch(Exception e){
            hasRole = false;
        }
        return ResponseEntity.ok(hasRole);
    }
}
