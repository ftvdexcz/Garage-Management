package com.garagemanagement.accessoryservice.internal;

import com.garagemanagement.accessoryservice.common.handler.AppError;
import com.garagemanagement.accessoryservice.common.model.internal.RetrieveUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InternalServiceImpl {
    @Autowired
    UserServiceProxy userServiceProxy;


    public RetrieveUserDTO checkUserRoleById(String id, String roles) {
        RetrieveUserDTO retrieveUserDTO = userServiceProxy.getUserById(id);

        if (retrieveUserDTO == null || retrieveUserDTO.getId() == null)
            throw AppError.BadRequestError("User id is invalid: " + id);

        String role = retrieveUserDTO.getRole();

        String[] r = roles.split(",");

        for (String i : r) {
            if (role.equalsIgnoreCase(i))
                return retrieveUserDTO;
        }

        throw AppError.UnauthorizedError();
    }

    public void checkUserHasRole(String token, String roles) {
        Boolean hasRole = userServiceProxy.checkUserHasRole(token, roles);
        if (!hasRole)
            throw AppError.UnauthorizedError();
    }

}
