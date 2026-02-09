package com.flipfit.dao;

import com.flipfit.entity.Booking;
import org.jdbi.v3.core.Jdbi;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class BookingDAOImpl implements BookingDAO {

    private final Jdbi jdbi;

    public BookingDAOImpl(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public Booking addBooking(Booking booking) {
        String sql = "INSERT INTO bookings (customer_id, center_id, slot_id, booking_date, status) VALUES (:customerId, :centerId, :slotId, :bookingDate, 'PENDING') RETURNING id";
        return jdbi.withHandle(handle -> {
            int bookingId = handle.createUpdate(sql)
                    .bind("customerId", booking.getCustomerId())
                    .bind("centerId", booking.getCenterId())
                    .bind("slotId", booking.getSlotId())
                    .bind("bookingDate", booking.getBookingDate())
                    .executeAndReturnGeneratedKeys("id")
                    .mapTo(Integer.class)
                    .one();
            booking.setBookingId(bookingId);
            return booking;
        });
    }

    @Override
    public boolean confirmBooking(int bookingId) {
        String sql = "UPDATE bookings SET status = 'CONFIRMED' WHERE id = :id";
        return jdbi.withHandle(handle -> {
            int rows = handle.createUpdate(sql)
                    .bind("id", bookingId)
                    .execute();
            return rows > 0;
        });
    }

    @Override
    public boolean cancelBooking(int bookingId) {
        String sql = "UPDATE bookings SET status = 'CANCELLED' WHERE id = :id";
        return jdbi.withHandle(handle -> {
            int rows = handle.createUpdate(sql)
                    .bind("id", bookingId)
                    .execute();
            return rows > 0;
        });
    }

    @Override
    public Booking getBookingById(int bookingId) {
        String sql = "SELECT * FROM bookings WHERE id = :id";
        return jdbi.withHandle(handle ->
                Objects.requireNonNull(handle.createQuery(sql)
                        .bind("id", bookingId)
                        .mapToBean(Booking.class)
                        .findOne()
                        .orElse(null)
                )
        );
    }

    @Override
    public List<Booking> getBookingsByCustomer(int customerId) {
        String sql = "SELECT * FROM bookings WHERE customer_id = :customerId";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("customerId", customerId)
                        .mapToBean(Booking.class)
                        .list()
        );
    }

    @Override
    public List<Booking> getBookingsBySlots(int slotId) {
        String sql = "SELECT * FROM bookings WHERE slot_id = :slotId";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("slotId", slotId)
                        .mapToBean(Booking.class)
                        .list()
        );
    }

    @Override
    public List<Booking> getBookingsByCenter(int centerId) {
        String sql = "SELECT * FROM bookings WHERE center_id = :centerId";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("centerId", centerId)
                        .mapToBean(Booking.class)
                        .list()
        );
    }

    @Override
    public List<Booking> getBookingsByDate(LocalDate date) {
        String sql = "SELECT * FROM bookings WHERE booking_date = :date";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("date", date)
                        .mapToBean(Booking.class)
                        .list()
        );
    }
}
