package com.garagemanagement.carrepairservice.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.garagemanagement.carrepairservice.common.entity.Car;
import com.garagemanagement.carrepairservice.common.model.internal.RetrieveUserDTO;
import com.garagemanagement.carrepairservice.internal.InternalServiceImpl;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CarRepairPaginationDTO {
    private String id;
    private Boolean status;
    private Date date;
    private String note;

    @JsonProperty("employee_id")
    private String employeeId;

//    @JsonProperty("employee_name")
//    private String employeeName;

    @JsonProperty("customer_id")
    private String customerId;

//    @JsonProperty("customer_name")
//    private String customerName;

    @JsonProperty("car_id")
    private String carId;

    @JsonProperty("plate")
    private String plate;

    public CarRepairPaginationDTO(
            String id, Boolean status, Car car, Date date,
            String employeeId, String note
            ){

        this.id = id;
        this.status = status;
        this.carId = car.getId();
        this.plate = car.getPlate();
        this.date = date;
        this.employeeId = employeeId;
        this.note = note;
        this.customerId = car.getCustomerId();

    }
}
