package com.flipfit.service;

import com.flipfit.dao.AdminDAO;
import com.flipfit.entity.GymCenter;
import com.flipfit.entity.User;

import java.util.List;

public class AdminServiceImpl implements AdminService {

    private final AdminDAO adminDAO;

    public AdminServiceImpl(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }

    @Override
    public void approveGymCenter(int centerId) {
        boolean approved = adminDAO.approveGymCenter(centerId);
        if (!approved) {
            throw new RuntimeException("Failed to approve gym center with ID: " + centerId);
        }
    }

    @Override
    public void declineGymCenter(int centerId) {
        boolean declined = adminDAO.declineGymCenter(centerId);
        if (!declined) {
            throw new RuntimeException("Failed to decline gym center with ID: " + centerId);
        }
    }

    @Override
    public List<GymCenter> getPendingGymCenters() {
        return adminDAO.getPendingGymCenters();
    }

    @Override
    public List<GymCenter> getAllGymCenters() {
        return adminDAO.getAllGymCenters();
    }

    @Override
    public List<User> getAllUsers() {
        return adminDAO.getAllUsers();
    }

    @Override
    public String getReport() {
        return adminDAO.getReport();
    }
}
