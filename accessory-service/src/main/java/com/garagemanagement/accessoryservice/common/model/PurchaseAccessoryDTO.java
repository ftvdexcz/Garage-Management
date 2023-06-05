package com.garagemanagement.accessoryservice.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseAccessoryDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    @Min(value = 0, message = "price must be large than 0")
    private int quantity;

    @Min(value = 0, message = "amount must be large than 0")
    private double amount;

    @JsonProperty("purchased_date")
    @NotNull(message = "purchased date must be not empty")
    private Date purchasedDate;

    @JsonProperty("employee_id")
    @NotNull(message = "employee id must be not empty")
    private String employeeId;
}
