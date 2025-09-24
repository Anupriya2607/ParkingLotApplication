package com.anupriya.parkinglot.controller;

import com.anupriya.parkinglot.dto.ParkRequest;
import com.anupriya.parkinglot.dto.PaymentResponse;
import com.anupriya.parkinglot.dto.TicketResponse;
import com.anupriya.parkinglot.entity.Payment;
import com.anupriya.parkinglot.entity.Ticket;
import com.anupriya.parkinglot.service.ParkingService;
import com.anupriya.parkinglot.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parking")
public class ParkingController {

    private final ParkingService parkingService;
    private final PaymentService paymentService;

    public ParkingController(ParkingService parkingService, PaymentService paymentService) {
        this.parkingService = parkingService;
        this.paymentService = paymentService;
    }

    @PostMapping("/park")
    public TicketResponse park(@Valid @RequestBody ParkRequest request,
                               @AuthenticationPrincipal OidcUser user) {
        Ticket ticket = parkingService.parkVehicle(request.getPlateNo(), request.getVehicleType(), user.getSubject());
        return Mapper.toTicketResponse(ticket);
    }

    @PostMapping("/exit/{ticketId}")
    public TicketResponse exit(@PathVariable Long ticketId) {
        Ticket ticket = parkingService.markExit(ticketId);
        return Mapper.toTicketResponse(ticket);
    }

    @PostMapping("/pay/{ticketId}")
    public PaymentResponse pay(@PathVariable Long ticketId) {
        Payment payment = paymentService.pay(ticketId);
        return Mapper.toPaymentResponse(payment);
    }

    @GetMapping("/ticket/{ticketId}")
    public TicketResponse getTicket(@PathVariable Long ticketId) {
        Ticket t = parkingService.getActiveTicket(ticketId); // only active tickets accessible here
        return Mapper.toTicketResponse(t);
    }
}

