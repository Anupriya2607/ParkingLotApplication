package com.anupriya.parkinglot.dto;

import com.anupriya.parkinglot.model.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ParkRequest {
    @NotBlank
    private String plateNo;
    @NotNull
    private VehicleType vehicleType;

    public String getPlateNo() { return plateNo; }
    public void setPlateNo(String plateNo) { this.plateNo = plateNo; }
    public VehicleType getVehicleType() { return vehicleType; }
    public void setVehicleType(VehicleType vehicleType) { this.vehicleType = vehicleType; }
}

