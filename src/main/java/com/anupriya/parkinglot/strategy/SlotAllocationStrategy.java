package com.anupriya.parkinglot.strategy;

import com.anupriya.parkinglot.entity.ParkingSlot;
import com.anupriya.parkinglot.model.VehicleType;

public interface SlotAllocationStrategy {
    ParkingSlot allocate(VehicleType vehicleType);
}

