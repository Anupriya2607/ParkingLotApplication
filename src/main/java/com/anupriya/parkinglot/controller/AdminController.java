package com.anupriya.parkinglot.controller;

import com.anupriya.parkinglot.dto.AddSlotRequest;
import com.anupriya.parkinglot.dto.PricingUpdateRequest;
import com.anupriya.parkinglot.entity.ParkingLot;
import com.anupriya.parkinglot.entity.ParkingSlot;
import com.anupriya.parkinglot.service.ParkingService;
import com.anupriya.parkinglot.service.PricingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final ParkingService parkingService;
    private final PricingService pricingService;

    public AdminController(ParkingService parkingService, PricingService pricingService) {
        this.parkingService = parkingService;
        this.pricingService = pricingService;
    }

    @PostMapping("/slots")
    public Map<String,Object> addSlot(@Valid @RequestBody AddSlotRequest request) {
        ParkingSlot slot = parkingService.addSlot(request.getVehicleType(), request.getFloor());
        return Map.of("slotId", slot.getId(), "type", slot.getType(), "floor", slot.getFloorNumber());
    }

    @DeleteMapping("/slots/{id}")
    public Map<String,String> removeSlot(@PathVariable Long id) {
        parkingService.removeSlot(id);
        return Map.of("status","REMOVED");
    }

    @PatchMapping("/pricing")
    public Map<String,Object> updatePricing(@RequestBody PricingUpdateRequest req) {
        ParkingLot lot = pricingService.updatePricing(req.getBikeRatePerHour(), req.getCarRatePerHour(), req.getTruckRatePerHour(), req.getFreeMinutes());
        return pricingMap(lot);
    }

    @GetMapping("/pricing")
    public Map<String,Object> getPricing() {
        return pricingMap(pricingService.getActiveLot());
    }

    private Map<String,Object> pricingMap(ParkingLot lot) {
        return Map.of(
                "parkingLotId", lot.getId(),
                "bikeRatePerHour", lot.getBikeRatePerHour(),
                "carRatePerHour", lot.getCarRatePerHour(),
                "truckRatePerHour", lot.getTruckRatePerHour(),
                "freeMinutes", lot.getFreeMinutes()
        );
    }
}

