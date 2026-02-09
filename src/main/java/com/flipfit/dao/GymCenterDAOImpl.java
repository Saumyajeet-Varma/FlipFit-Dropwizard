package com.flipfit.dao;

import com.flipfit.entity.GymCenter;
import com.flipfit.entity.Slot;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
import java.util.Objects;

public class GymCenterDAOImpl implements GymCenterDAO {

    private final Jdbi jdbi;

    public GymCenterDAOImpl(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public boolean addGymCenter(GymCenter center) {
        String sql = "INSERT INTO gym_centers (name, location, owner_id, status) VALUES (:name, :location, :ownerId, 'PENDING')";
        return jdbi.withHandle(handle -> {
            int rows = handle.createUpdate(sql)
                    .bind("name", center.getName())
                    .bind("location", center.getLocation())
                    .bind("ownerId", center.getOwnerId())
                    .execute();
            return rows > 0;
        });
    }

    @Override
    public GymCenter getGymCenterById(int centerId) {
        String sql = "SELECT * FROM gym_centers WHERE id = :id";
        return jdbi.withHandle(handle ->
                Objects.requireNonNull(handle.createQuery(sql)
                        .bind("id", centerId)
                        .mapToBean(GymCenter.class)
                        .findOne()
                        .orElse(null)
                )
        );
    }

    @Override
    public List<GymCenter> getAllGymCenters() {
        String sql = "SELECT * FROM gym_centers";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .mapToBean(GymCenter.class)
                        .list()
        );
    }

    @Override
    public List<GymCenter> getPendingGymCenters() {
        String sql = "SELECT * FROM gym_centers WHERE status = 'PENDING'";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .mapToBean(GymCenter.class)
                        .list()
        );
    }

    @Override
    public List<GymCenter> getGymCentersByOwner(int ownerId) {
        String sql = "SELECT * FROM gym_centers WHERE owner_id = :ownerId";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("ownerId", ownerId)
                        .mapToBean(GymCenter.class)
                        .list()
        );
    }

    @Override
    public boolean approveGymCenter(int centerId) {
        String sql = "UPDATE gym_centers SET status = 'APPROVED' WHERE id = :id";
        return jdbi.withHandle(handle -> {
            int rows = handle.createUpdate(sql)
                    .bind("id", centerId)
                    .execute();
            return rows > 0;
        });
    }

    @Override
    public boolean rejectGymCenter(int centerId) {
        String sql = "UPDATE gym_centers SET status = 'DECLINED' WHERE id = :id";
        return jdbi.withHandle(handle -> {
            int rows = handle.createUpdate(sql)
                    .bind("id", centerId)
                    .execute();
            return rows > 0;
        });
    }

    @Override
    public List<Slot> getAvailableSlots(int centerId) {
        String sql = "SELECT * FROM slots WHERE center_id = :centerId AND available_seats > 0";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("centerId", centerId)
                        .mapToBean(Slot.class)
                        .list()
        );
    }

    @Override
    public boolean requestForApproval(int centerId) {
        String sql = "UPDATE gym_centers SET status = 'PENDING' WHERE id = :id";
        return jdbi.withHandle(handle -> {
            int rows = handle.createUpdate(sql)
                    .bind("id", centerId)
                    .execute();
            return rows > 0;
        });
    }
}
