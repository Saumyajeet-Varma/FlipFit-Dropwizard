package com.flipfit.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class Booking {

    @NotNull
    private int bookingId;

    @NotNull
    private int userId;

    @NotNull
    private int slotId;

    @NotNull
    private int centerId;

    @NotNull
    private BookingStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate bookingDate;

    public Booking() {}

    public Booking(int bookingId, int userId, int slotId, int centerId, LocalDate bookingDate, BookingStatus status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.slotId = slotId;
        this.centerId = centerId;
        this.bookingDate = bookingDate;
        this.status = status;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}
