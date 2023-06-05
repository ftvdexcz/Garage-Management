package com.garagemanagement.userservice.controller;

import com.garagemanagement.userservice.common.constant.StatusCodeResponse;
import com.garagemanagement.userservice.common.constant.UserRole;
import com.garagemanagement.userservice.common.handler.AppError;
import com.garagemanagement.userservice.common.handler.ResponseObject;
import com.garagemanagement.userservice.common.model.CreateUserDTO;
import com.garagemanagement.userservice.common.model.RetrieveUserDTO;
import com.garagemanagement.userservice.service.AuthService;
import com.garagemanagement.userservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getUserById(@PathVariable String id) {
        RetrieveUserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(ResponseObject.successResponseWithData(user));
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> createUser(HttpServletRequest request, @Valid @RequestBody CreateUserDTO createUserDTO) {
        // Nếu không phải admin thì chỉ có thể đăng kí với Role là Customer
//        if (!authService.checkRole(request, UserRole.ADMIN.toString())) {
//            createUserDTO.setRole(3);
//        }

        RetrieveUserDTO createdUserDTO = userService.createUser(createUserDTO);
        return ResponseEntity.ok(ResponseObject.successResponseWithData(createdUserDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteUserById(HttpServletRequest request, @PathVariable String id) {
        if (!authService.checkRole(request, UserRole.ADMIN.toString())) throw AppError.UnauthorizedError();

        userService.deleteUserById(id);
        return ResponseEntity.status(StatusCodeResponse.STATUS_RESPONSE_DELETED_OK).
                body(ResponseObject.successResponseWithData(null));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseObject> updateUserById(HttpServletRequest request, @PathVariable String id,
                                                         @RequestBody Map<String, Object> fields) {
        // Nếu không phải Admin thì không được đổi Role
        boolean isAdmin = authService.checkRole(request, UserRole.ADMIN.toString());

        RetrieveUserDTO updatedUser = userService.updateUserById(id, fields, isAdmin);
        return ResponseEntity.status(StatusCodeResponse.STATUS_RESPONSE_CREATED_OK).
                body(ResponseObject.successResponseWithData(updatedUser));
    }
}
