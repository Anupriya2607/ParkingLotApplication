package com.anupriya.parkinglot.entity;

import com.anupriya.parkinglot.model.SlotStatus;
import com.anupriya.parkinglot.model.VehicleType;
import jakarta.persistence.*;

@Entity
@Table(indexes = {
        @Index(name = "idx_slot_type_status", columnList = "type,status"),
        @Index(name = "idx_slot_floor", columnList = "floorNumber")
})
public class ParkingSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private VehicleType type;

    private int floorNumber;

    @Enumerated(EnumType.STRING)
    private SlotStatus status = SlotStatus.AVAILABLE;

    @Version
    private Long version;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public VehicleType getType() { return type; }
    public void setType(VehicleType type) { this.type = type; }
    public int getFloorNumber() { return floorNumber; }
    public void setFloorNumber(int floorNumber) { this.floorNumber = floorNumber; }
    public SlotStatus getStatus() { return status; }
    public void setStatus(SlotStatus status) { this.status = status; }
    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }
}

