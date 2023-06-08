package com.garagemanagement.carrepairservice.common.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Table(name = "bill")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "amount", nullable = false)
    private double amount;

    @JsonProperty("payment_date")
    @Column(name = "payment_date", nullable = false)
    private Date paymentDate;

    @OneToOne
    @JoinColumn(name = "car_repair_id", nullable = false)
    private CarRepair carRepair;
}

