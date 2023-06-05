package com.garagemanagement.accessoryservice.common.entity;

import com.garagemanagement.accessoryservice.common.model.PurchaseAccessoryDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "accessory_purchased")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccessoryPurchased {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "purchased_date", nullable = false)
    private Date purchasedDate;

    @Column(name = "employee_id", nullable = false)
    private String employeeId;

    @ManyToOne
    @JoinColumn(name = "accessory_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Accessory accessory;


}
