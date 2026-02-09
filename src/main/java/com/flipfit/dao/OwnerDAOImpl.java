package com.flipfit.dao;

import com.flipfit.entity.Owner;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
import java.util.Objects;

public class OwnerDAOImpl implements OwnerDAO {

    private final Jdbi jdbi;

    public OwnerDAOImpl(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public boolean addOwner(Owner owner) {
        String sql = "INSERT INTO users (name, email, password, role) VALUES (:name, :email, :password, 'OWNER')";
        return jdbi.inTransaction(handle -> {
            int rows = handle.createUpdate(sql)
                    .bind("name", owner.getName())
                    .bind("email", owner.getEmail())
                    .bind("password", owner.getPassword())
                    .execute();
            return rows > 0;
        });
    }

    @Override
    public Owner getOwnerById(int ownerId) {
        String sql = "SELECT * FROM users WHERE id = :id AND role = 'OWNER'";
        return jdbi.withHandle(handle ->
                Objects.requireNonNull(handle.createQuery(sql)
                        .bind("id", ownerId)
                        .mapToBean(Owner.class)
                        .findOne()
                        .orElse(null)
                )
        );
    }

    @Override
    public List<Owner> getAllOwners() {
        String sql = "SELECT * FROM users WHERE role = 'OWNER'";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .mapToBean(Owner.class)
                        .list()
        );
    }
}
