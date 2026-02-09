package com.flipfit.dao;

import com.flipfit.entity.GymCenter;
import com.flipfit.entity.Slot;

import java.util.List;

public interface GymCenterDAO {

    boolean addGymCenter(GymCenter center);

    GymCenter getGymCenterById(int centerId);

    List<GymCenter> getAllGymCenters();

    List<GymCenter> getPendingGymCenters();

    List<GymCenter> getGymCentersByOwner(int ownerId);

    boolean approveGymCenter(int centerId);

    boolean rejectGymCenter(int centerId);

    List<Slot> getAvailableSlots(int centerId);

    boolean requestForApproval(int centerId);
}