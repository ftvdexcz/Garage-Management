package com.garagemanagement.accessoryservice.common.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "service")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceEntity {
    @Id
    @Column(name = "id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    @Column(name = "name", nullable = false)
    @NotEmpty(message = "name must be not empty")
    private String name;

    @Column(name = "price", nullable = false)
    @Min(value = 0, message = "price must be large than 0")
    private double price;

    @Column(name = "note")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String note;
}
