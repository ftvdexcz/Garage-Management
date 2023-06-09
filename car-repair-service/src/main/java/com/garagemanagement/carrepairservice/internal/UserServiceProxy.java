package com.garagemanagement.carrepairservice.internal;

import com.garagemanagement.carrepairservice.common.model.internal.CreateUserDTO;
import com.garagemanagement.carrepairservice.common.model.internal.RetrieveUserDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@FeignClient(name = "user-service", url = "http://localhost:8000")
@FeignClient(name = "user-service")

// Change in kurbenetes
//@FeignClient(name = "user-service", url="${USER_SERVICE_HOST:http://localhost}:8000")
public interface UserServiceProxy {
    @PostMapping("/internal/user")
    RetrieveUserDTO createUser(
            @Valid @RequestBody CreateUserDTO createUserDTO
    );

    @GetMapping("/internal/user/phone/{phone}")
    RetrieveUserDTO getUserByPhone(
            @PathVariable String phone
    );

    @GetMapping("/internal/user/{id}")
    RetrieveUserDTO getUserById(
            @PathVariable String id
    );

    @GetMapping("/internal/users")
    List<RetrieveUserDTO> getAllUsers(

    );
    /*
     * Check role từ request:
     * Header Authorization gửi token "Bearer <token>"
     * Header "x-user-role" gửi những role được cho phép, format: "ADMIN,SUPPORT,CUSTOMER"
     * */
    @PostMapping("/internal/auth/check-role")
    Boolean checkUserHasRole(
            @RequestHeader("Authorization") String token,
            @RequestHeader("x-user-role") String roles
    );
}
