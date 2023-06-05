package com.garagemanagement.accessoryservice.common.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.garagemanagement.accessoryservice.common.model.CreateAccessoryDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "accessory")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Accessory {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "image_source")
    private String imageSource;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    // chỉ cho phép supplier hiển thị accessories chứ không cho chiều ngược lại để tránh đệ quy
    // bên supplier thêm @JsonManagedReference
    @JsonBackReference
    private Supplier supplier;
}
