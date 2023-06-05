package com.garagemanagement.userservice.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    @NotEmpty(message = "Username must be not empty")
    private String username;

    @NotEmpty(message = "Password must be not empty")
    private String password;

    @NotEmpty(message = "Fullname must be not empty")
    private String fullname;

    @NotEmpty(message = "Phone must be not empty")
    private String phone;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String address;

    @Range(min = 1, max = 3, message = "Invalid role")
    private int role;
}