package com.garagemanagement.accessoryservice.internal;

import com.garagemanagement.accessoryservice.common.model.internal.RetrieveUserDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

//@FeignClient(name = "user-service", url = "http://localhost:8000")
@FeignClient(name = "user-service")
public interface UserServiceProxy {
    @GetMapping("/internal/user/{id}")
    RetrieveUserDTO getUserById(
            @PathVariable String id
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
