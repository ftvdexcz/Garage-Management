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
* Chức năng đặt lịch online (role: customer)
* customer_id (để tạo car nếu chưa có)
* car_plate
* ngày
* dịch vụ sử dụng
* */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAppointmentCarRepairDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    @JsonProperty("customer_id")
    @NotEmpty(message = "customer id must be not empty")
    private String customerId;

    @NotNull(message = "date must be not empty")
    private Date date;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String plate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String[] services;
}
