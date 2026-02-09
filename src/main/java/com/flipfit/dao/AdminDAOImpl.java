package com.flipfit.dao;

import com.flipfit.entity.Admin;
import com.flipfit.entity.GymCenter;
import com.flipfit.entity.Owner;
import com.flipfit.entity.User;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
import java.util.Objects;

public class AdminDAOImpl implements AdminDAO {

    private final Jdbi jdbi;

    public AdminDAOImpl(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public boolean addAdmin(Admin admin) {
        String sql = "INSERT INTO users (name, email, password, role) VALUES (:name, :email, :password, 'ADMIN')";
        return jdbi.withHandle(handle -> {
            int rows = handle.createUpdate(sql)
                    .bind("name", admin.getName())
                    .bind("email", admin.getEmail())
                    .bind("password", admin.getPassword())
                    .execute();
            return rows > 0;
        });
    }

    @Override
    public Admin getAdminById(int adminId) {
        String sql = "SELECT * FROM users WHERE id = :id AND role = 'ADMIN'";
        return jdbi.withHandle(handle ->
                Objects.requireNonNull(handle.createQuery(sql)
                        .bind("id", adminId)
                        .mapToBean(Admin.class)
                        .findOne()
                        .orElse(null)
                )
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
    public boolean declineGymCenter(int centerId) {
        String sql = "UPDATE gym_centers SET status = 'DECLINED' WHERE id = :id";
        return jdbi.withHandle(handle -> {
            int rows = handle.createUpdate(sql)
                    .bind("id", centerId)
                    .execute();
            return rows > 0;
        });
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .mapToBean(User.class)
                        .list()
        );
    }

    @Override
    public List<Owner> getALlGymOwners() {
        String sql = "SELECT * FROM users WHERE role = 'OWNER'";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .mapToBean(Owner.class)
                        .list()
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
    public boolean verifyGymOwner(int ownerId) {
        String sql = "UPDATE users SET is_verified = true WHERE id = :id AND role = 'OWNER'";
        return jdbi.withHandle(handle -> {
            int rows = handle.createUpdate(sql)
                    .bind("id", ownerId)
                    .execute();
            return rows > 0;
        });
    }

    @Override
    public String getReport() {
        return jdbi.withHandle(handle -> {

            String sql1 = "SELECT COUNT(*) FROM users WHERE role = 'CUSTOMER'";
            String sql2 = "SELECT COUNT(*) FROM users WHERE role = 'OWNER'";
            String sql3 = "SELECT COUNT(*) FROM gym_centers WHERE status = 'APPROVED'";
            String sql4 = "SELECT COUNT(*) FROM gym_centers WHERE status = 'PENDING'";
            String sql5 = "SELECT COUNT(*) FROM gym_centers WHERE status = 'DECLINED'";

            int customerCount = handle.createQuery(sql1).mapTo(Integer.class).one();
            int ownerCount    = handle.createQuery(sql2).mapTo(Integer.class).one();
            int approvedGyms  = handle.createQuery(sql3).mapTo(Integer.class).one();
            int pendingGyms   = handle.createQuery(sql4).mapTo(Integer.class).one();
            int declinedGyms  = handle.createQuery(sql5).mapTo(Integer.class).one();

            return String.format(
                    "=== FlipFit System Report ===\n" +
                            "Total Customers:  %d\n" +
                            "Total Owners:     %d\n" +
                            "Gym Centers (Approved): %d\n" +
                            "Gym Centers (Pending):  %d\n" +
                            "Gym Centers (Declined): %d",
                    customerCount, ownerCount, approvedGyms, pendingGyms, declinedGyms
            );
        });
    }
}
