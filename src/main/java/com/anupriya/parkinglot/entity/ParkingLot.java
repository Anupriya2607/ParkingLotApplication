package com.anupriya.parkinglot.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;

    private int floors;

    // Pricing (per hour) by vehicle type
    private BigDecimal bikeRatePerHour = BigDecimal.valueOf(10);
    private BigDecimal carRatePerHour = BigDecimal.valueOf(20);
    private BigDecimal truckRatePerHour = BigDecimal.valueOf(40);

    // Free minutes before charges apply
    private int freeMinutes = 0;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public int getFloors() { return floors; }
    public void setFloors(int floors) { this.floors = floors; }
    public BigDecimal getBikeRatePerHour() { return bikeRatePerHour; }
    public void setBikeRatePerHour(BigDecimal bikeRatePerHour) { this.bikeRatePerHour = bikeRatePerHour; }
    public BigDecimal getCarRatePerHour() { return carRatePerHour; }
    public void setCarRatePerHour(BigDecimal carRatePerHour) { this.carRatePerHour = carRatePerHour; }
    public BigDecimal getTruckRatePerHour() { return truckRatePerHour; }
    public void setTruckRatePerHour(BigDecimal truckRatePerHour) { this.truckRatePerHour = truckRatePerHour; }
    public int getFreeMinutes() { return freeMinutes; }
    public void setFreeMinutes(int freeMinutes) { this.freeMinutes = freeMinutes; }
}

