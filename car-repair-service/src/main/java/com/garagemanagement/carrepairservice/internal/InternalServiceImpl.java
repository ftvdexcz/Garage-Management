package com.garagemanagement.carrepairservice.internal;

import com.garagemanagement.carrepairservice.common.handler.AppError;
import com.garagemanagement.carrepairservice.common.model.internal.AccessoryDTO;
import com.garagemanagement.carrepairservice.common.model.internal.RetrieveUserDTO;
import com.garagemanagement.carrepairservice.common.model.internal.ServiceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InternalServiceImpl {
    @Autowired
    UserServiceProxy userServiceProxy;

    @Autowired
    AccessoryServiceProxy accessoryServiceProxy;

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

    public AccessoryDTO getAccessoryById(String id){
        AccessoryDTO accessory = accessoryServiceProxy.getAccessoryById(id);

        if(accessory == null || accessory.getId() == null)
            throw AppError.BadRequestError("Accessory id is invalid: " + id);

        return accessory;
    }

    public ServiceDTO getServiceById(String id){
        ServiceDTO service = accessoryServiceProxy.getServiceById(id);

        if(service == null || service.getId() == null)
            throw AppError.BadRequestError("Service id is invalid: " + id);

        return service;
    }
}
