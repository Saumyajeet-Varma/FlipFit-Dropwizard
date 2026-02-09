package com.flipfit.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Slot {

    private int slotId;
    private int centerId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int totalSeats;
    private int availableSeats;

    public Slot() {}

    public Slot(int slotId, int centerId, LocalDate date, LocalTime startTime, LocalTime endTime, int totalSeats, int availableSeats) {
        this.slotId = slotId;
        this.centerId = centerId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public int getCenterId() {
        return centerId;
    }

    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}
