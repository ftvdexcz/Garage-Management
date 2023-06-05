package com.garagemanagement.userservice.controller;

import com.garagemanagement.userservice.common.handler.ResponseObject;
import com.garagemanagement.userservice.common.model.RetrieveUserDTO;
import com.garagemanagement.userservice.service.AuthService;
import com.garagemanagement.userservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal")
public class InternalController {
    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @GetMapping("/user/{id}")
    public ResponseEntity<RetrieveUserDTO> getUserById(@PathVariable String id) {
        RetrieveUserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
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
