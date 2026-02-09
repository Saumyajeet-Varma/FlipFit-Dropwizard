package com.flipfit.dao;

import com.flipfit.entity.User;

import java.util.List;

public interface UserDAO {

    boolean addUser(User user);

    User getUserById(int userId);

    User getUserByEmail(String email);

    List<User> getAllUsers();
}
