package com.flipfit.dao;

import com.flipfit.entity.Admin;
import com.flipfit.entity.Customer;
import com.flipfit.entity.Owner;
import com.flipfit.entity.User;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.util.List;
import java.util.Objects;

public class UserDAOImpl implements UserDAO {

    private final Jdbi jdbi;

    public UserDAOImpl(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public boolean addUser(User user) {
        String sql = "INSERT INTO users (name, email, password, role) VALUES (:name, :email, :password, :role)";
        String role = user.getClass().getSimpleName().toUpperCase();
        return jdbi.inTransaction(handle -> {
            int rows = handle.createUpdate(sql)
                    .bind("name", user.getName())
                    .bind("email", user.getEmail())
                    .bind("password", user.getPassword())
                    .bind("role", role)
                    .execute();
            return rows > 0;
        });
    }

    @Override
    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE id = :id";
        return jdbi.withHandle(handle->
                Objects.requireNonNull(handle.createQuery(sql)
                        .bind("id", userId)
                        .map(new UserMapper())
                        .findOne()
                        .orElse(null)
                )
        );
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = :email";
        return jdbi.withHandle(handle ->
                Objects.requireNonNull(handle.createQuery(sql)
                        .bind("email", email)
                        .map(new UserMapper())
                        .findOne()
                        .orElse(null)
                )
        );
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .map(new UserMapper())
                        .list()
        );
    }

    public static class UserMapper implements RowMapper<User> {
        @Override
        public User map(ResultSet rs, StatementContext ctx) throws java.sql.SQLException {

            String role = rs.getString("role");
            User user;

            switch(role.toUpperCase()) {
                case "ADMIN":
                    user = new Admin();
                    break;
                case "OWNER":
                    user = new Customer();
                    break;
                case "CUSTOMER":
                    user = new Owner();
                    break;
                default:
                    throw new IllegalArgumentException("Unknown role: " + role);
            }

            user.setUserId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));

            return user;
        }
    }
}
