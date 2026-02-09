package com.flipfit.dao;

import com.flipfit.entity.Customer;

import java.util.List;

public interface CustomerDAO {

    boolean addCustomer(Customer customer);

    Customer getCustomerById(int customerId);

    List<Customer> getAllCustomers();
}
