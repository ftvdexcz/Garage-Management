package com.garagemanagement.carrepairservice.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.garagemanagement.carrepairservice.common.entity.AccessoryUsed;
import com.garagemanagement.carrepairservice.common.entity.ServiceUsed;
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
public class ServiceUsedDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    @Min(value = 0, message = "amount must be large than 0")
    private double amount;

    @JsonProperty("service_id")
    @NotEmpty(message = "service id must be not empty")
    private String serviceId;

    @JsonProperty("car_repair_id")
    @NotEmpty(message = "car repair id must be not empty")
    private String carRepairId;

    public static ServiceUsedDTO retrieveServiceUsed(ServiceUsed serviceUsed){
        return new ServiceUsedDTO(
                serviceUsed.getId(),
                serviceUsed.getAmount(),
                serviceUsed.getServiceId(),
                serviceUsed.getCarRepair().getId()
        );
    }
}
