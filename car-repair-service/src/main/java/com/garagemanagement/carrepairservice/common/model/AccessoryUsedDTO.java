package com.garagemanagement.carrepairservice.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.garagemanagement.carrepairservice.common.entity.AccessoryUsed;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccessoryUsedDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    @Min(value = 0, message = "quantity must be large than 0")
    private int quantity;

    @Min(value = 0, message = "amount must be large than 0")
    private double amount;

    @JsonProperty("accessory_id")
    @NotEmpty(message = "accessory id must be not empty")
    private String accessoryId;

    @JsonProperty("car_repair_id")
    @NotEmpty(message = "car repair id must be not empty")
    private String carRepairId;

    public static AccessoryUsedDTO retrieveAccessoryUsed(AccessoryUsed accessoryUsed){
        return new AccessoryUsedDTO(
                accessoryUsed.getId(),
                accessoryUsed.getQuantity(),
                accessoryUsed.getAmount(),
                accessoryUsed.getAccessoryId(),
                accessoryUsed.getCarRepair().getId()
        );
    }
}
