package com.anupriya.parkinglot.strategy;

import com.anupriya.parkinglot.entity.ParkingSlot;
import com.anupriya.parkinglot.model.SlotStatus;
import com.anupriya.parkinglot.model.VehicleType;
import com.anupriya.parkinglot.repository.ParkingSlotRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class NearestSlotAllocationStrategy implements SlotAllocationStrategy {

    private final ParkingSlotRepository slotRepository;

    public NearestSlotAllocationStrategy(ParkingSlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    @Override
    @Transactional
    public ParkingSlot allocate(VehicleType vehicleType) {
        // Try a bounded number of attempts to avoid infinite loops under heavy contention
        for (int attempt = 0; attempt < 5; attempt++) {
            List<ParkingSlot> candidates = slotRepository
                    .findTop10ByTypeAndStatusOrderByFloorNumberAscIdAsc(vehicleType, SlotStatus.AVAILABLE);
            for (ParkingSlot candidate : candidates) {
                int updated = slotRepository.occupySlot(candidate.getId());
                if (updated == 1) {
                    // reload entity to return managed instance with updated state
                    return slotRepository.findById(candidate.getId()).orElseThrow();
                }
            }
            if (candidates.isEmpty()) {
                return null; // no slot available for this type
            }
        }
        return null; // could not allocate due to contention
    }
}

