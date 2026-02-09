package com.flipfit.dao;

import com.flipfit.entity.Admin;
import com.flipfit.entity.GymCenter;
import com.flipfit.entity.Owner;
import com.flipfit.entity.User;

import java.util.List;

public interface AdminDAO {

    boolean addAdmin(Admin admin);

    Admin getAdminById(int adminId);

    List<GymCenter> getPendingGymCenters();

    boolean approveGymCenter(int centerId);

    boolean declineGymCenter(int centerId);

    List<User> getAllUsers();

    List<Owner> getALlGymOwners();

    List<GymCenter> getAllGymCenters();

    boolean verifyGymOwner(int ownerId);

    String getReport();
}
