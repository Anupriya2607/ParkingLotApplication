package com.anupriya.parkinglot.repository;

import com.anupriya.parkinglot.entity.ParkingSlot;
import com.anupriya.parkinglot.model.SlotStatus;
import com.anupriya.parkinglot.model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {
    List<ParkingSlot> findTop10ByTypeAndStatusOrderByFloorNumberAscIdAsc(VehicleType type, SlotStatus status);

    long countByTypeAndStatus(VehicleType type, SlotStatus status);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update ParkingSlot s set s.status = com.anupriya.parkinglot.model.SlotStatus.OCCUPIED where s.id=:id and s.status = com.anupriya.parkinglot.model.SlotStatus.AVAILABLE")
    int occupySlot(@Param("id") Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update ParkingSlot s set s.status = com.anupriya.parkinglot.model.SlotStatus.AVAILABLE where s.id=:id and s.status = com.anupriya.parkinglot.model.SlotStatus.OCCUPIED")
    int freeSlot(@Param("id") Long id);
}

