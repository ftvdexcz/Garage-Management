package com.garagemanagement.carrepairservice.common.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Table(name = "car_repair")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarRepair {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "status", nullable = false)
    private boolean status;

    @Column(name = "date")
    private Date date;

    @Column(name = "note")
    private String note;

    @Column(name = "employee_id")
    private String employeeId;

    @OneToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @OneToMany(mappedBy = "carRepair", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    private List<AccessoryUsed> accessoryUseds;

    @OneToMany(mappedBy = "carRepair", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    private List<ServiceUsed> serviceUseds;

    public CarRepair(
            String id,
            boolean status,
            Date date,
            String note,
            String employeeId,
            Car car
    ) {
        this.id = id;
        this.status = status;
        this.date = date;
        this.note = note;
        this.employeeId = employeeId;
        this.car = car;
    }
}
