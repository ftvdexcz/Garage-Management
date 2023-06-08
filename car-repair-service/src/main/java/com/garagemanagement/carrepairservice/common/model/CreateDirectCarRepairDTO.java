package com.garagemanagement.carrepairservice.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/*
* Chức năng nhận khách trực tếp (role: admin, support)
* customer_name
* customer_phone
* car_plate
* ngày
* employee_id (phân công nhân viên kĩ thuật)
* accessories (chọn linh kiện)
* */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDirectCarRepairDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    @JsonProperty("customer_name")
    @NotEmpty(message = "customer name must be not empty")
    private String customerName;

    @JsonProperty("customer_phone")
    @NotEmpty(message = "customer phone must be not empty")
    private String customerPhone;

    @NotNull(message = "date must be not empty")
    private Date date;

    @NotEmpty(message = "plate must be not empty")
    private String plate;

    @JsonProperty("employee_id")
    @NotEmpty(message = "employee id must be not empty")
    private String employeeId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String[] accessories;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String[] services;
}
