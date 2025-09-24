package com.anupriya.parkinglot.exception;

public class AlreadyParkedException extends RuntimeException {
    public AlreadyParkedException(String plate) { super("Vehicle already parked: " + plate); }
}

