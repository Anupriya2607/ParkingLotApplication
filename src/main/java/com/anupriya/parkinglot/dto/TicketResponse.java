package com.anupriya.parkinglot.dto;

import com.anupriya.parkinglot.model.TicketStatus;
import com.anupriya.parkinglot.model.VehicleType;

import java.time.Instant;

public class TicketResponse {
    private Long ticketId;
    private String plateNo;
    private VehicleType vehicleType;
    private Long slotId;
    private int floor;
    private Instant entryTime;
    private Instant exitTime;
    private TicketStatus status;

    public Long getTicketId() { return ticketId; }
    public void setTicketId(Long ticketId) { this.ticketId = ticketId; }
    public String getPlateNo() { return plateNo; }
    public void setPlateNo(String plateNo) { this.plateNo = plateNo; }
    public VehicleType getVehicleType() { return vehicleType; }
    public void setVehicleType(VehicleType vehicleType) { this.vehicleType = vehicleType; }
    public Long getSlotId() { return slotId; }
    public void setSlotId(Long slotId) { this.slotId = slotId; }
    public int getFloor() { return floor; }
    public void setFloor(int floor) { this.floor = floor; }
    public Instant getEntryTime() { return entryTime; }
    public void setEntryTime(Instant entryTime) { this.entryTime = entryTime; }
    public Instant getExitTime() { return exitTime; }
    public void setExitTime(Instant exitTime) { this.exitTime = exitTime; }
    public TicketStatus getStatus() { return status; }
    public void setStatus(TicketStatus status) { this.status = status; }
}

