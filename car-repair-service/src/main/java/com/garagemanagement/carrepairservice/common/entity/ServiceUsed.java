package com.garagemanagement.carrepairservice.common.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Table
@Entity(name = "service_used")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceUsed {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "amount", nullable = false)
    private double amount;

    @JsonProperty("service_id")
    @Column(name = "service_id", nullable = false)
    private String serviceId;

    @ManyToOne
    @JoinColumn(name = "car_repair_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference
    private CarRepair carRepair;
}
