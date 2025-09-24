package com.anupriya.parkinglot.entity;

import com.anupriya.parkinglot.model.VehicleType;
import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "uk_vehicle_plate", columnNames = "plateNo"))
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 32)
    private String plateNo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType type;

    private String ownerId; // maps to authenticated principal name / sub

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPlateNo() { return plateNo; }
    public void setPlateNo(String plateNo) { this.plateNo = plateNo; }
    public VehicleType getType() { return type; }
    public void setType(VehicleType type) { this.type = type; }
    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }
}

