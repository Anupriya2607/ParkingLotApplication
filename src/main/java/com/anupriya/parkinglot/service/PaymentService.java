package com.anupriya.parkinglot.service;

import com.anupriya.parkinglot.entity.ParkingSlot;
import com.anupriya.parkinglot.entity.Payment;
import com.anupriya.parkinglot.entity.Ticket;
import com.anupriya.parkinglot.exception.NotFoundException;
import com.anupriya.parkinglot.model.PaymentStatus;
import com.anupriya.parkinglot.model.TicketStatus;
import com.anupriya.parkinglot.repository.ParkingSlotRepository;
import com.anupriya.parkinglot.repository.PaymentRepository;
import com.anupriya.parkinglot.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class PaymentService {

    private final TicketRepository ticketRepository;
    private final PaymentRepository paymentRepository;
    private final ParkingSlotRepository slotRepository;
    private final PricingService pricingService;

    public PaymentService(TicketRepository ticketRepository,
                          PaymentRepository paymentRepository,
                          ParkingSlotRepository slotRepository,
                          PricingService pricingService) {
        this.ticketRepository = ticketRepository;
        this.paymentRepository = paymentRepository;
        this.slotRepository = slotRepository;
        this.pricingService = pricingService;
    }

    @Transactional
    public Payment pay(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NotFoundException("Ticket not found: " + ticketId));
        if (ticket.getStatus() != TicketStatus.ACTIVE) {
            // Already closed -> return payment if exists
            return paymentRepository.findByTicket(ticket)
                    .orElseThrow(() -> new IllegalStateException("Ticket already closed but no payment record."));
        }
        if (ticket.getExitTime() == null) {
            ticket.setExitTime(Instant.now());
        }
        Payment existing = paymentRepository.findByTicket(ticket).orElse(null);
        if (existing != null && existing.getStatus() == PaymentStatus.SUCCESS) {
            return existing; // idempotent
        }
        BigDecimal amount = pricingService.calculateAmount(ticket.getEntryTime(), ticket.getExitTime(), ticket.getVehicle().getType());
        Payment payment = existing != null ? existing : new Payment();
        payment.setTicket(ticket);
        payment.setAmount(amount);
        payment.setTimestamp(Instant.now());
        // Simulate external gateway success
        payment.setStatus(PaymentStatus.SUCCESS);
        Payment saved = paymentRepository.save(payment);
        // Free slot & close ticket atomically (only after payment success)
        ParkingSlot slot = ticket.getSlot();
        int updated = slotRepository.freeSlot(slot.getId());
        if (updated == 1) {
            ticket.setStatus(TicketStatus.CLOSED);
        } else {
            throw new IllegalStateException("Failed to free slot; concurrent modification detected.");
        }
        return saved;
    }
}

