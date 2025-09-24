package com.anupriya.parkinglot.service;

import com.anupriya.parkinglot.entity.ParkingLot;
import com.anupriya.parkinglot.model.VehicleType;
import com.anupriya.parkinglot.repository.ParkingLotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

@Service
public class PricingService {

    private final ParkingLotRepository lotRepository;

    public PricingService(ParkingLotRepository lotRepository) { this.lotRepository = lotRepository; }

    public ParkingLot getActiveLot() {
        // Assuming single lot use-case; pick first.
        return lotRepository.findAll().stream().findFirst().orElseThrow(() -> new IllegalStateException("Parking lot not configured"));
    }

    @Transactional
    public ParkingLot updatePricing(BigDecimal bike, BigDecimal car, BigDecimal truck, Integer freeMinutes) {
        ParkingLot lot = getActiveLot();
        if (bike != null) lot.setBikeRatePerHour(bike);
        if (car != null) lot.setCarRatePerHour(car);
        if (truck != null) lot.setTruckRatePerHour(truck);
        if (freeMinutes != null) lot.setFreeMinutes(freeMinutes);
        return lotRepository.save(lot);
    }

    public BigDecimal calculateAmount(Instant entry, Instant exit, VehicleType type) {
        ParkingLot lot = getActiveLot();
        long totalMinutes = Duration.between(entry, exit).toMinutes();
        int free = lot.getFreeMinutes();
        if (totalMinutes <= free) return BigDecimal.ZERO;
        long chargeable = totalMinutes - free;
        long hours = (chargeable + 59) / 60; // ceiling hours
        BigDecimal rate;
        switch (type) {
            case BIKE -> rate = lot.getBikeRatePerHour();
            case CAR -> rate = lot.getCarRatePerHour();
            case TRUCK -> rate = lot.getTruckRatePerHour();
            default -> throw new IllegalArgumentException("Unsupported vehicle type");
        }
        return rate.multiply(BigDecimal.valueOf(hours));
    }
}

