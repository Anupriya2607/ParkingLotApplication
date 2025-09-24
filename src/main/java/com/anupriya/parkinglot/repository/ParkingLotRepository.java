package com.anupriya.parkinglot.repository;

import com.anupriya.parkinglot.entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {
}

