package com.flipfit.dao;

import com.flipfit.entity.Customer;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
import java.util.Objects;

public class CustomerDAOImpl implements CustomerDAO {

    private final Jdbi jdbi;

    public CustomerDAOImpl(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO users (name, email, password, role) VALUES (:name, :email, :password, 'CUSTOMER')";
        return jdbi.inTransaction(handle -> {
            int rows = handle.createUpdate(sql)
                    .bind("name", customer.getName())
                    .bind("email", customer.getEmail())
                    .bind("password", customer.getPassword())
                    .execute();
            return rows > 0;
        });
    }

    @Override
    public Customer getCustomerById(int customerId) {
        String sql = "SELECT * FROM users WHERE id = :id AND role = 'CUSTOMER'";
        return jdbi.withHandle(handle ->
                Objects.requireNonNull(handle.createQuery(sql)
                        .bind("id", customerId)
                        .mapToBean(Customer.class)
                        .findOne()
                        .orElse(null)
                )
        );
    }

    @Override
    public List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM users WHERE role = 'CUSTOMER'";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .mapToBean(Customer.class)
                        .list()
        );
    }
}
