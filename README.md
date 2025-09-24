# ParkingLotApplication 

A Java-based parking lot management application.  
This project provides the core logic to allocate, free, and manage parking spots.  

---

## Table of Contents

- [Features](#features)  
- [Architecture & Design](#architecture--design)  
- [Getting Started](#getting-started)  
  - [Prerequisites](#prerequisites)  
  - [Installation](#installation)  
- [Usage](#usage)  
- [Database Schema](#Database--Schema)
- [Transaction Handling in Spring Boot](#Transaction--Handling)
- [Contributing](#contributing)  
- [Contact](#contact)  

---

## Features

- Allocate a vehicle to a parking spot  
- Free up parking spots  
- Query occupancy / list free slots  
- Support for different vehicle types (if implemented)  
- Simple command-line or API interface (depending on your project)  

---

## Architecture & Design

This project follows a modular, clean architecture. The major layers/components include:

- **Models / Domain** — Entities like `ParkingLot`, `Slot`, `Vehicle`, etc.  
- **Service / Core Logic** — Algorithms for allocation, deallocation, searching spots.  
- **Adapters / Interface** — Input / output handlers (CLI, REST, etc.).  
- **Persistence** — (If applicable) storage or in‑memory maps.  

You may follow Dependency Injection, SOLID principles and keep your modules loosely coupled.

---

## Getting Started

### Prerequisites

- JDK 8+ (or version your project uses)  
- Maven (if you use Maven)  
- (Optional) An IDE like IntelliJ IDEA, Eclipse, or VS Code  

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/Anupriya2607/ParkingLotApplication.git
   cd ParkingLotApplication
Build using Maven (or your build tool):

bash
Copy code
mvn clean install
(Optional) Run from IDE by running the main class (if present).

### Database--Schema
CREATE TABLE parking_lot (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    location VARCHAR(255) NOT NULL,
    floors INT NOT NULL
);

CREATE TABLE parking_slot (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(20) NOT NULL,  -- ENUM: CAR, BIKE, TRUCK
    floor INT NOT NULL,
    status VARCHAR(20) DEFAULT 'AVAILABLE', -- ENUM: AVAILABLE, OCCUPIED
    parking_lot_id BIGINT NOT NULL,
    FOREIGN KEY (parking_lot_id) REFERENCES parking_lot(id)
);

CREATE TABLE vehicle (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    plate_no VARCHAR(50) UNIQUE NOT NULL,
    type VARCHAR(20) NOT NULL, -- ENUM: CAR, BIKE, TRUCK
    owner_id BIGINT
);

CREATE TABLE ticket (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    vehicle_id BIGINT NOT NULL,
    slot_id BIGINT NOT NULL,
    entry_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    exit_time TIMESTAMP NULL,
    status VARCHAR(20) DEFAULT 'ACTIVE', -- ENUM: ACTIVE, COMPLETED
    FOREIGN KEY (vehicle_id) REFERENCES vehicle(id),
    FOREIGN KEY (slot_id) REFERENCES parking_slot(id)
);

CREATE TABLE payment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    ticket_id BIGINT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING', -- ENUM: PENDING, SUCCESS, FAILED
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ticket_id) REFERENCES ticket(id)
);




 ### Transaction Handling in Spring Boot

In a parking lot system, transactions are critical.
For example: when a vehicle is parked, we must:
Allocate a slot (status = OCCUPIED).
Save vehicle details.
Create a ticket.
If any step fails, everything should roll back.


model — domain classes

service — core logic

controller or interface — interaction layer

App.java or equivalent — main entry class


### Contributing
Contributions are welcome! Please follow these steps:

Fork the repository

Create a new branch: git checkout -b feature/YourFeature

Make changes / add tests

Commit changes: git commit -m "Add <feature>"

Push to your fork: git push origin feature/YourFeature

Open a Pull Request


### Contact
Author: Anupriya Singh

GitHub: https://github.com/Anupriya2607

Email: anshikasingh2610@gmail.com
