package com.anupriya.parkinglot.config;

import com.anupriya.parkinglot.entity.ParkingLot;
import com.anupriya.parkinglot.entity.ParkingSlot;
import com.anupriya.parkinglot.model.SlotStatus;
import com.anupriya.parkinglot.model.VehicleType;
import com.anupriya.parkinglot.repository.ParkingLotRepository;
import com.anupriya.parkinglot.repository.ParkingSlotRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ParkingLotRepository lotRepository;
    private final ParkingSlotRepository slotRepository;

    public DataInitializer(ParkingLotRepository lotRepository, ParkingSlotRepository slotRepository) {
        this.lotRepository = lotRepository;
        this.slotRepository = slotRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (lotRepository.count() == 0) {
            ParkingLot lot = new ParkingLot();
            lot.setLocation("Central City");
            lot.setFloors(3);
            lotRepository.save(lot);
            // Floor distribution: Floor 0: bikes, Floor 1: cars, Floor 2: trucks
            for (int i=0;i<5;i++) { addSlot(VehicleType.BIKE,0); }
            for (int i=0;i<2;i++) { addSlot(VehicleType.CAR,1); }
            for (int i=0;i<1;i++) { addSlot(VehicleType.TRUCK,2); }
        }
    }

    private void addSlot(VehicleType type, int floor) {
        ParkingSlot s = new ParkingSlot();
        s.setType(type);
        s.setFloorNumber(floor);
        s.setStatus(SlotStatus.AVAILABLE);
        slotRepository.save(s);
    }
}

