package com.anupriya.parkinglot.repository;

import com.anupriya.parkinglot.entity.Ticket;
import com.anupriya.parkinglot.entity.Vehicle;
import com.anupriya.parkinglot.model.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    boolean existsByVehicleAndStatus(Vehicle vehicle, TicketStatus status);
    Optional<Ticket> findByIdAndStatus(Long id, TicketStatus status);
    List<Ticket> findByVehicleAndStatus(Vehicle vehicle, TicketStatus status);
}

