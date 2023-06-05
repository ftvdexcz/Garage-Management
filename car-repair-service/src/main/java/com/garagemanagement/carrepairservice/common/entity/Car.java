package com.garagemanagement.carrepairservice.common.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "car")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "id")
    private String id;

    @NotEmpty(message = "plate must be not empty")
    @Column(name = "plate")
    private String plate;

    @Column(name = "customer_id", nullable = false)
    @NotEmpty(message = "customer id must be not empty")
    @JsonProperty("customer_id")
    private String customerId;
}
