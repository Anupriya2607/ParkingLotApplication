package com.anupriya.parkinglot.dto;

import java.math.BigDecimal;

public class PricingUpdateRequest {
    private BigDecimal bikeRatePerHour;
    private BigDecimal carRatePerHour;
    private BigDecimal truckRatePerHour;
    private Integer freeMinutes;

    public BigDecimal getBikeRatePerHour() { return bikeRatePerHour; }
    public void setBikeRatePerHour(BigDecimal bikeRatePerHour) { this.bikeRatePerHour = bikeRatePerHour; }
    public BigDecimal getCarRatePerHour() { return carRatePerHour; }
    public void setCarRatePerHour(BigDecimal carRatePerHour) { this.carRatePerHour = carRatePerHour; }
    public BigDecimal getTruckRatePerHour() { return truckRatePerHour; }
    public void setTruckRatePerHour(BigDecimal truckRatePerHour) { this.truckRatePerHour = truckRatePerHour; }
    public Integer getFreeMinutes() { return freeMinutes; }
    public void setFreeMinutes(Integer freeMinutes) { this.freeMinutes = freeMinutes; }
}

