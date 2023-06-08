package com.garagemanagement.carrepairservice.common.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Table
@Entity(name = "accessory_used")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccessoryUsed {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "amount", nullable = false)
    private double amount;

    @JsonProperty("accessory_id")
    @Column(name = "accessory_id", nullable = false)
    private String accessoryId;

    @ManyToOne
    @JoinColumn(name = "car_repair_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference
    private CarRepair carRepair;
}
