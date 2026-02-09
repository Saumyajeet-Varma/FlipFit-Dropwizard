package com.flipfit.entity;

public class Admin extends User {

    public Admin() {}

    public Admin(int userId, String name, String email, String password) {
        this.setUserId(userId);
        this.setName(name);
        this.setEmail(email);
        this.setPassword(password);
    }
}
