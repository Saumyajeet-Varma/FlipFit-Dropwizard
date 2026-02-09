package com.flipfit.entity;

import java.util.List;

public class Customer extends User {

    private List<Slot> bookedSlots;

    public Customer() {}

    public Customer(int userId, String name, String email, String password) {
        this.setUserId(userId);
        this.setName(name);
        this.setEmail(email);
        this.setPassword(password);
    }

    public List<Slot> getBookedSlots() {
        return bookedSlots;
    }

    public void setBookedSlots(List<Slot> bookedSlots) {
        this.bookedSlots = bookedSlots;
    }
}
