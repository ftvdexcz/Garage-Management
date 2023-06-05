package com.garagemanagement.userservice.service;

import com.garagemanagement.userservice.common.model.RetrieveUserDTO;
import com.garagemanagement.userservice.common.model.UserLoginDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface AuthService {
    public boolean checkRole(HttpServletRequest request, String role);

    public boolean checkClientHasRoleAccessResource(HttpServletRequest request, String resourceId);

    public String logout(HttpServletRequest request);

    public RetrieveUserDTO verifyToken(HttpServletRequest request);

    public Map<String, String> login(UserLoginDTO userLoginDTO);
}
