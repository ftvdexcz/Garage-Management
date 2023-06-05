package com.garagemanagement.userservice.common.model;

import com.garagemanagement.userservice.common.constant.UserRole;
import com.garagemanagement.userservice.common.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveUserDTO {
    private String id;
    private String username;
    private String fullname;
    private String phone;
    private String email;
    private String address;
    private String role;

    public RetrieveUserDTO(String id, String username, String fullname, String phone, String email, String address){
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public static String withRole(int role){
        UserRole convertedRole = UserRole.fromRole(role);
        return convertedRole.toString();
    }


    public static RetrieveUserDTO retrieveUser(User user) {
        RetrieveUserDTO retrieveUserDTO =  new RetrieveUserDTO(
                user.getId(),
                user.getUsername(),
                user.getFullname(),
                user.getPhone(),
                user.getEmail(),
                user.getAddress()
        );
        retrieveUserDTO.setRole(withRole(user.getRole()));

        return retrieveUserDTO;
    }
}
