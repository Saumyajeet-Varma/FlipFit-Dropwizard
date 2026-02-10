package com.flipfit.service;

import com.flipfit.entity.GymCenter;
import com.flipfit.entity.User;

import java.util.List;

public interface AdminService {

    void approveGymCenter(int centerId);

    void declineGymCenter(int centerId);

    List<GymCenter> getPendingGymCenters();

    List<GymCenter> getAllGymCenters();

    List<User> getAllUsers();

    String getReport();
}
