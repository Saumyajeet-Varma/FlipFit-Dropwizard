package com.flipfit.dao;

import com.flipfit.entity.Slot;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
import java.util.Objects;

public class SlotDAOImpl implements SlotDAO {

    private final Jdbi jdbi;

    public SlotDAOImpl(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public Slot createSlot(int centerId, Slot slot) {
        String sql = "INSERT INTO slots (center_id, start_time, end_time, available_seats, total_seats) VALUES (:centerId, :startTime, :endTime, :availableSeats, :total_seats) RETURNING id";
        return jdbi.withHandle(handle -> {
            int slotId = handle.createUpdate(sql)
                    .bind("centerId", centerId)
                    .bind("startTime", slot.getStartTime())
                    .bind("endTime", slot.getEndTime())
                    .bind("availableSeats", slot.getAvailableSeats())
                    .bind("total_seats", slot.getTotalSeats())
                    .executeAndReturnGeneratedKeys("id")
                    .mapTo(Integer.class)
                    .one();
            slot.setSlotId(slotId);
            slot.setCenterId(centerId);
            return slot;
        });
    }

    @Override
    public Slot getSlotById(int slotId) {
        String sql = "SELECT * FROM slots WHERE id = :id";
        return jdbi.withHandle(handle ->
                Objects.requireNonNull(handle.createQuery(sql)
                        .bind("id", slotId)
                        .mapToBean(Slot.class)
                        .findOne()
                        .orElse(null)
                )
        );
    }

    @Override
    public List<Slot> getSlotsByCenter(int centerId) {
        String sql = "SELECT * FROM slots WHERE center_id = :centerId";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("centerId", centerId)
                        .mapToBean(Slot.class)
                        .list()
        );
    }

    @Override
    public List<Slot> getAvailableSlotsByCenter(int centerId) {
        String sql = "SELECT * FROM slots WHERE center_id = :centerId AND available_seats > 0";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("centerId", centerId)
                        .mapToBean(Slot.class)
                        .list()
        );
    }

    @Override
    public boolean isSlotAvailable(int slotId) {
        String sql = "SELECT available_seats FROM slots WHERE id = :id";
        return jdbi.withHandle(handle -> {
            int rows = handle.createQuery(sql)
                    .bind("id", slotId)
                    .mapTo(Integer.class)
                    .findOne()
                    .orElse(0);
            return rows > 0;
        });
    }

    @Override
    public boolean lockSlot(int slotId) {
        String sql = "UPDATE slots SET available_seats = available_seats - 1 WHERE id = :id AND available_seats > 0";
        return jdbi.inTransaction(handle -> {
            int rows = handle.createUpdate(sql)
                    .bind("id", slotId)
                    .execute();
            return rows > 0;
        });
    }

    @Override
    public boolean unlockSlot(int slotId) {
        String sql = "UPDATE slots SET available_seats = available_seats + 1 WHERE id = :id";
        return jdbi.inTransaction(handle -> {
            int rows = handle.createUpdate(sql)
                    .bind("id", slotId)
                    .execute();
            return rows > 0;
        });
    }

    @Override
    public boolean deleteSlot(int slotId) {
        String sql = "DELETE FROM slots WHERE id = :id";
        return jdbi.inTransaction(handle -> {
            int rows = handle.createUpdate(sql)
                    .bind("id", slotId)
                    .execute();
            return rows > 0;
        });
    }
}
