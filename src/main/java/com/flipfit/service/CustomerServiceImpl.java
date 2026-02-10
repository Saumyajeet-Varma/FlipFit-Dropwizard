package com.flipfit.service;

import com.flipfit.dao.BookingDAO;
import com.flipfit.dao.CustomerDAO;
import com.flipfit.dao.GymCenterDAO;
import com.flipfit.dao.SlotDAO;
import com.flipfit.entity.Booking;
import com.flipfit.entity.BookingStatus;
import com.flipfit.entity.GymCenter;
import com.flipfit.entity.Slot;

import java.time.LocalDate;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO customerDAO;
    private final GymCenterDAO gymCenterDAO;
    private final SlotDAO slotDAO;
    private final BookingDAO bookingDAO;

    public CustomerServiceImpl(CustomerDAO customerDAO, GymCenterDAO gymCenterDAO, SlotDAO slotDAO, BookingDAO bookingDAO) {
        this.customerDAO = customerDAO;
        this.gymCenterDAO = gymCenterDAO;
        this.slotDAO = slotDAO;
        this.bookingDAO = bookingDAO;
    }

    @Override
    public List<GymCenter> getAllGymCenters() {
        return gymCenterDAO.getAllGymCenters();
    }

    @Override
    public List<Slot> getAvailableSlots(int centerId) {
        return slotDAO.getAvailableSlotsByCenter(centerId);
    }

    @Override
    public List<Booking> getMyBookings(int customerId) {
        return bookingDAO.getBookingsByCustomer(customerId);
    }

    @Override
    public boolean bookSlot(int customerId, int slotId) {

        if(!slotDAO.isSlotAvailable(slotId)) {
            System.out.println("Slot is not available");
            return false;
        }

        boolean lockedSlot = slotDAO.lockSlot(slotId);

        if(lockedSlot) {

            Slot slot = slotDAO.getSlotById(slotId);

            Booking booking = new Booking();
            booking.setCustomerId(customerId);
            booking.setSlotId(slotId);
            booking.setCenterId(slot.getCenterId());
            booking.setBookingDate(LocalDate.now());
            booking.setStatus(BookingStatus.valueOf("CONFIRMED"));

            Booking savedBooking = bookingDAO.addBooking(booking);
            return savedBooking != null;
        }

        return false;
    }

    @Override
    public boolean cancelBooking(int bookingId) {

        Booking booking = bookingDAO.getBookingById(bookingId);

        if(booking == null || "CANCELLED".equals(booking.getStatus().name())) {
            System.out.println("Booking not found or already cancelled");
            return false;
        }

        boolean isCancelled = bookingDAO.cancelBooking(bookingId);

        if(isCancelled) {
            slotDAO.unlockSlot(booking.getSlotId());
            return true;
        }

        return false;
    }
}
