package com.garagemanagement.userservice.service.impl;

import com.garagemanagement.userservice.common.config.security.JwtTokenProvider;
import com.garagemanagement.userservice.common.constant.UserRole;
import com.garagemanagement.userservice.common.entity.User;
import com.garagemanagement.userservice.common.handler.AppError;
import com.garagemanagement.userservice.common.model.RetrieveUserDTO;
import com.garagemanagement.userservice.common.model.UserLoginDTO;
import com.garagemanagement.userservice.common.utils.HashedPassword;
import com.garagemanagement.userservice.repository.UserRepository;
import com.garagemanagement.userservice.service.AuthService;
import com.garagemanagement.userservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    private String authenticate(HttpServletRequest request) {
        String token = jwtTokenProvider.getTokenFromRequest(request);

        String decoded =  jwtTokenProvider.validateToken(token);

        if(decoded == null){
            throw AppError.ForbiddenError();
        };

        return decoded;
    }

    // Check role match or not
    @Override
    public boolean checkRole(HttpServletRequest request, String roles) {
        String id = authenticate(request);

        RetrieveUserDTO user = userService.getUserById(id);

        String[] roles_arr = roles.split(",");

        for(String r: roles_arr){
            if (r.equalsIgnoreCase(user.getRole())) return true;
        }
        return false;
    }

    @Override
    public boolean checkClientHasRoleAccessResource(HttpServletRequest request, String resourceId){
        String id = authenticate(request);

        RetrieveUserDTO user = userService.getUserById(id);

        String role = user.getRole();

        if (role.equalsIgnoreCase(UserRole.ADMIN.toString())) return true;
        else if(role.equalsIgnoreCase(UserRole.CUSTOMER.toString())){
            return user.getId().equalsIgnoreCase(resourceId);
        }
        return false;
    }

    @Override
    public String logout(HttpServletRequest request) {
        String token = jwtTokenProvider.getTokenFromRequest(request);

        String decoded =  jwtTokenProvider.validateToken(token);

        if(decoded == null){
            throw AppError.ForbiddenError();
        };

        // delete token here (return newToken to client)
        return jwtTokenProvider.deleteToken(token);
    }

    @Override
    public Map<String, String> login(UserLoginDTO userLoginDTO){
        Optional<User> user = userRepository.findByUsername(userLoginDTO.getUsername());

        if(user.isPresent()){
            String password = user.get().getPassword();
            if(HashedPassword.decryptPassword(userLoginDTO.getPassword(), password)){
                return jwtTokenProvider.signToken(user.get());
            }
        }

        throw AppError.LoginError();
    }

    public RetrieveUserDTO verifyToken(HttpServletRequest request){
        String id = authenticate(request);

        return userService.getUserById(id);
    }
}
