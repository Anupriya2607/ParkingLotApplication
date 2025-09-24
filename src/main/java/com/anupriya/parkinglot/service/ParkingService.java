package com.anupriya.parkinglot.service;

import com.anupriya.parkinglot.entity.ParkingSlot;
import com.anupriya.parkinglot.entity.Ticket;
import com.anupriya.parkinglot.entity.Vehicle;
import com.anupriya.parkinglot.repository.ParkingSlotRepository;
import com.anupriya.parkinglot.repository.TicketRepository;
import com.anupriya.parkinglot.repository.VehicleRepository;
import com.anupriya.parkinglot.entity.*;
import com.anupriya.parkinglot.exception.AlreadyParkedException;
import com.anupriya.parkinglot.exception.NotFoundException;
import com.anupriya.parkinglot.model.SlotStatus;
import com.anupriya.parkinglot.model.TicketStatus;
import com.anupriya.parkinglot.model.VehicleType;
import com.anupriya.parkinglot.repository.*;
import com.anupriya.parkinglot.strategy.SlotAllocationStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class ParkingService {

    private final VehicleRepository vehicleRepository;
    private final TicketRepository ticketRepository;
    private final ParkingSlotRepository slotRepository;
    private final SlotAllocationStrategy allocationStrategy;

    public ParkingService(VehicleRepository vehicleRepository,
                          TicketRepository ticketRepository,
                          ParkingSlotRepository slotRepository,
                          SlotAllocationStrategy allocationStrategy) {
        this.vehicleRepository = vehicleRepository;
        this.ticketRepository = ticketRepository;
        this.slotRepository = slotRepository;
        this.allocationStrategy = allocationStrategy;
    }

    @Transactional
    public Ticket parkVehicle(String plateNo, VehicleType type, String ownerId) {
        Vehicle vehicle = vehicleRepository.findByPlateNoIgnoreCase(plateNo)
                .orElseGet(() -> {
                    Vehicle v = new Vehicle();
                    v.setPlateNo(plateNo);
                    v.setType(type);
                    v.setOwnerId(ownerId);
                    return vehicleRepository.save(v);
                });
        if (ticketRepository.existsByVehicleAndStatus(vehicle, TicketStatus.ACTIVE)) {
            throw new AlreadyParkedException(plateNo);
        }
        if (vehicle.getType() != type) {
            // update type if changed (extensible scenario)
            vehicle.setType(type);
        }
        ParkingSlot slot = allocationStrategy.allocate(type);
        if (slot == null) {
            throw new IllegalStateException("No available slot for type: " + type);
        }
        Ticket ticket = new Ticket();
        ticket.setVehicle(vehicle);
        ticket.setSlot(slot);
        ticket.setEntryTime(Instant.now());
        ticket.setStatus(TicketStatus.ACTIVE);
        return ticketRepository.save(ticket);
    }

    @Transactional(readOnly = true)
    public Ticket getActiveTicket(Long id) {
        return ticketRepository.findByIdAndStatus(id, TicketStatus.ACTIVE)
                .orElseThrow(() -> new NotFoundException("Active ticket not found: " + id));
    }

    @Transactional
    public Ticket markExit(Long ticketId) {
        Ticket ticket = getActiveTicket(ticketId);
        if (ticket.getExitTime() == null) {
            ticket.setExitTime(Instant.now());
        }
        return ticket;
    }

    @Transactional
    public ParkingSlot addSlot(VehicleType type, int floor) {
        ParkingSlot slot = new ParkingSlot();
        slot.setType(type);
        slot.setFloorNumber(floor);
        slot.setStatus(SlotStatus.AVAILABLE);
        return slotRepository.save(slot);
    }

    @Transactional
    public void removeSlot(Long slotId) {
        ParkingSlot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new NotFoundException("Slot not found: " + slotId));
        if (slot.getStatus() == SlotStatus.OCCUPIED) {
            throw new IllegalStateException("Cannot remove occupied slot");
        }
        slotRepository.delete(slot);
    }
}

