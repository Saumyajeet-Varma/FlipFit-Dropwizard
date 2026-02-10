package com.flipfit.service;

import com.flipfit.entity.GymCenter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface OwnerService {

    void addGymCenter(int ownerId, String name, String location);

    void requestGymCenterApproval(int centerId);

    List<GymCenter> getMyGymCenters(int ownerId);

    void addSlot(int centerId, LocalDate date, LocalTime startTime, LocalTime endTime, int totalSeats);

    void removeSlot(int slotId);
}
