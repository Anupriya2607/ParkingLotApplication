package com.anupriya.parkinglot.controller;

import com.anupriya.parkinglot.dto.PaymentResponse;
import com.anupriya.parkinglot.dto.TicketResponse;
import com.anupriya.parkinglot.entity.Payment;
import com.anupriya.parkinglot.entity.Ticket;

public final class Mapper {
    private Mapper() {}

    public static TicketResponse toTicketResponse(Ticket t) {
        TicketResponse r = new TicketResponse();
        r.setTicketId(t.getId());
        r.setPlateNo(t.getVehicle().getPlateNo());
        r.setVehicleType(t.getVehicle().getType());
        r.setSlotId(t.getSlot().getId());
        r.setFloor(t.getSlot().getFloorNumber());
        r.setEntryTime(t.getEntryTime());
        r.setExitTime(t.getExitTime());
        r.setStatus(t.getStatus());
        return r;
    }

    public static PaymentResponse toPaymentResponse(Payment p) {
        PaymentResponse r = new PaymentResponse();
        r.setPaymentId(p.getId());
        r.setTicketId(p.getTicket().getId());
        r.setAmount(p.getAmount());
        r.setStatus(p.getStatus());
        r.setTimestamp(p.getTimestamp());
        return r;
    }
}

