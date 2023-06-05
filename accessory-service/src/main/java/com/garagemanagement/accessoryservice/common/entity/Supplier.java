package com.garagemanagement.accessoryservice.common.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.garagemanagement.accessoryservice.common.model.CreateSupplierDTO;
import com.garagemanagement.accessoryservice.service.SupplierService;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "supplier")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    // chỉ cho phép supplier hiển thị accessories chứ không cho chiều ngược lại để tránh đệ quy
    // bên accessory thêm @JsonBackReference
    @JsonManagedReference
    private List<Accessory> accessories;

    public Supplier(String id, String name, String phone, String email, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public static Supplier createSupplier(CreateSupplierDTO createSupplierDTO){
        return new Supplier(
                createSupplierDTO.getId(),
                createSupplierDTO.getName(),
                createSupplierDTO.getPhone(),
                createSupplierDTO.getEmail(),
                createSupplierDTO.getAddress()
        );
    }
}
