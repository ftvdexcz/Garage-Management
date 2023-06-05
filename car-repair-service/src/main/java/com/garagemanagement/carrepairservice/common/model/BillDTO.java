package com.garagemanagement.carrepairservice.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    @Min(value = 0, message = "amount must be large than 0")
    private double amount;

    @JsonProperty("payment_date")
    @NotNull(message = "payment date must be not empty")
    private Date paymentDate;

    @JsonProperty("car_repair_id")
    @NotEmpty(message = "car repair id must be not empty")
    private String carRepairId;

}
