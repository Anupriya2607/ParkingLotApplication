package com.anupriya.parkinglot.dto;

import com.anupriya.parkinglot.model.VehicleType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AddSlotRequest {
    @NotNull
    private VehicleType vehicleType;
    @Min(0)
    private int floor;

    public VehicleType getVehicleType() { return vehicleType; }
    public void setVehicleType(VehicleType vehicleType) { this.vehicleType = vehicleType; }
    public int getFloor() { return floor; }
    public void setFloor(int floor) { this.floor = floor; }
}

