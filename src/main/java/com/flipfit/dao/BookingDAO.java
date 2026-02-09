package com.flipfit.dao;

import com.flipfit.entity.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BookingDAO {

    Booking addBooking();

    boolean confirmBooking(int bookingId);

    boolean cancelBooking(int bookingId);

    Booking getBookingById(int bookingId);

    List<Booking> getBookingsByCustomer(int customerId);

    List<Booking> getBookingsBySlots(int slotId);

    List<Booking> getBookingsByCenter(int centerId);

    List<Booking> getBookingsByDate(LocalDate date);
}
