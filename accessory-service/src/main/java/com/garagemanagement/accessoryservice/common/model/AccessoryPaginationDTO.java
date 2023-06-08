package com.garagemanagement.accessoryservice.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.garagemanagement.accessoryservice.common.entity.Accessory;
import com.garagemanagement.accessoryservice.common.entity.Supplier;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class AccessoryPaginationDTO {
    private String id;
    private String name;
    private double price;
    private int quantity;

    @JsonProperty("image_source")
    private String imageSource;

    @JsonProperty("supplier_name")
    private String supplierName;

    @JsonProperty("supplier_id")
    private String supplierId;

    public AccessoryPaginationDTO(
            Accessory a, String supplierName, String supplierId
    ){
        this.id = a.getId();
        this.name = a.getName();
        this.price = a.getPrice();
        this.quantity = a.getQuantity();
        this.imageSource = a.getImageSource();
        this.supplierName = supplierName;
        this.supplierId = supplierId;
    }
}
