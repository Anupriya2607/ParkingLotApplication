package com.anupriya.parkinglot.repository;

import com.anupriya.parkinglot.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByPlateNoIgnoreCase(String plateNo);
    boolean existsByPlateNoIgnoreCase(String plateNo);
}

