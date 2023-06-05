package com.garagemanagement.userservice.common.entity;


import com.garagemanagement.userservice.common.model.CreateUserDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name ="id", nullable = false)
    private String id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "passwd", nullable = false)
    private String password;

    @Column(name = "fullname", nullable = false)
    private String fullname;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "role", nullable = false)
    private int role;


    public User(String username, String password, String fullname, String phone, String email, String address, int role) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.role = role;
    }

    public static User createUser(CreateUserDTO createUserDTO){
        return new User(
                createUserDTO.getId(),
                createUserDTO.getUsername(),
                createUserDTO.getPassword(),
                createUserDTO.getFullname(),
                createUserDTO.getPhone(),
                createUserDTO.getEmail(),
                createUserDTO.getAddress(),
                createUserDTO.getRole()
        );
    }
}
