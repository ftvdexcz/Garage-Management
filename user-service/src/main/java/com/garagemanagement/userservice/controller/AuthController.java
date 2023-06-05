package com.garagemanagement.userservice.controller;

import com.garagemanagement.userservice.common.handler.ResponseObject;
import com.garagemanagement.userservice.common.model.RetrieveUserDTO;
import com.garagemanagement.userservice.common.model.UserLoginDTO;
import com.garagemanagement.userservice.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/logout")
    public ResponseEntity<ResponseObject> logout(HttpServletRequest request) {
        String newToken = authService.logout(request);

        return ResponseEntity.ok(new ResponseObject("Logout!",
                new HashMap<String, String>() {
                    {
                        put("token", newToken);
                    }
                }));
    }

    @PostMapping("/verify-token")
    public ResponseEntity<ResponseObject> verifyToken(HttpServletRequest request) {
        RetrieveUserDTO retrieveUserDTO = authService.verifyToken(request);

        System.out.println("Token valid");
        return ResponseEntity.ok(new ResponseObject(
                "Success!", retrieveUserDTO
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(@RequestBody UserLoginDTO userDTO){
        Map<String, String> map = authService.login(userDTO);

        return ResponseEntity.ok(new ResponseObject(
                "Login Success!", map
        ));
    }


}
