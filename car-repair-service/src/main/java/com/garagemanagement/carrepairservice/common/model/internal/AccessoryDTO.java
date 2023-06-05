package com.garagemanagement.carrepairservice.common.model.internal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccessoryDTO {
    private String id;
    private String name;
    private double price;
    private int quantity;
    private String imageSource;
    private String supplierId;
}
