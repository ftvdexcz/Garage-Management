package com.garagemanagement.accessoryservice.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.garagemanagement.accessoryservice.common.entity.Accessory;
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
public class CreateAccessoryDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    @NotEmpty(message = "accessory name must be not empty")
    private String name;

    @Min(value = 0, message = "price must be large than 0")
    private double price;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Min(value = 0, message = "price must be large than 0")
    private int quantity;

    @JsonProperty("image_source")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String imageSource;

    @JsonProperty("supplier_id")
    @NotEmpty(message = "supplier id must be not empty")
    private String supplierId;

    public static CreateAccessoryDTO retrieveAccessory(Accessory accessory){
        return new CreateAccessoryDTO(
                accessory.getId(),
                accessory.getName(),
                accessory.getPrice(),
                accessory.getQuantity(),
                accessory.getImageSource(),
                accessory.getSupplier().getId()
        );
    }
}
