package com.flipfit.service;

import com.flipfit.entity.Booking;
import com.flipfit.entity.GymCenter;
import com.flipfit.entity.Slot;

import java.util.List;

public interface CustomerService {

    List<GymCenter> getAllGymCenters();

    List<Slot> getAvailableSlots(int centerId);

    List<Booking> getMyBookings(int customerId);

    boolean bookSlot(int customerId, int slotId);

    boolean cancelBooking(int bookingId);
}
