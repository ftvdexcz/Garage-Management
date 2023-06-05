package com.garagemanagement.carrepairservice.common.model.internal;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RetrieveUserDTO {
    private String id;
    private String username;
    private String fullname;
    private String phone;
    private String email;
    private String address;
    private String role;
}
