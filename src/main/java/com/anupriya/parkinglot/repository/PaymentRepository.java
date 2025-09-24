package com.anupriya.parkinglot.repository;

import com.anupriya.parkinglot.entity.Payment;
import com.anupriya.parkinglot.entity.Ticket;
import com.anupriya.parkinglot.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByTicket(Ticket ticket);
    long countByTicketAndStatus(Ticket ticket, PaymentStatus status);
}

