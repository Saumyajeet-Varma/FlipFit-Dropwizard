package com.flipfit.service;

import com.flipfit.dao.GymCenterDAO;
import com.flipfit.dao.OwnerDAO;
import com.flipfit.dao.SlotDAO;
import com.flipfit.entity.GymCenter;
import com.flipfit.entity.Slot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class OwnerServiceImpl implements OwnerService {

    private final OwnerDAO ownerDAO;
    private final SlotDAO slotDAO;
    private final GymCenterDAO gymCenterDAO;

    public OwnerServiceImpl(OwnerDAO ownerDAO, SlotDAO slotDAO, GymCenterDAO gymCenterDAO) {
        this.ownerDAO = ownerDAO;
        this.slotDAO = slotDAO;
        this.gymCenterDAO = gymCenterDAO;
    }

    @Override
    public void addGymCenter(int ownerId, String name, String location) {

        if(ownerDAO.getOwnerById(ownerId) == null) {
            throw new RuntimeException("Owner with ID " + ownerId + " does not exist.");
        }

        GymCenter gymCenter = new GymCenter();
        gymCenter.setOwnerId(ownerId);
        gymCenter.setName(name);
        gymCenter.setLocation(location);
        gymCenter.setSlots(null);

        boolean isAdded = gymCenterDAO.addGymCenter(gymCenter);
        if(!isAdded) {
            throw new RuntimeException("Failed to add gym center. Please try again.");
        }
    }

    @Override
    public void requestGymCenterApproval(int centerId) {
        boolean isRequested = gymCenterDAO.requestForApproval(centerId);
        if(!isRequested) {
            throw new RuntimeException("Failed to request approval. Gym Center might not exist.");
        }
    }

    @Override
    public List<GymCenter> getMyGymCenters(int ownerId) {
        return gymCenterDAO.getGymCentersByOwner(ownerId);
    }

    @Override
    public void addSlot(int centerId, LocalDate date, LocalTime startTime, LocalTime endTime, int totalSeats) {

        GymCenter gymCenter = gymCenterDAO.getGymCenterById(centerId);
        if(gymCenter == null) {
            throw new RuntimeException("Gym Center with ID " + centerId + " does not exist.");
        }

        Slot slot = new Slot();
        slot.setCenterId(centerId);
        slot.setDate(date);
        slot.setStartTime(startTime);
        slot.setEndTime(endTime);
        slot.setTotalSeats(totalSeats);
        slot.setAvailableSeats(totalSeats);

        Slot createdSlot = slotDAO.createSlot(centerId, slot);
        if(createdSlot == null) {
            throw new RuntimeException("Failed to add slot. Please try again.");
        }
    }

    @Override
    public void removeSlot(int slotId) {
        boolean isDeleted = slotDAO.deleteSlot(slotId);
        if(!isDeleted) {
            throw new RuntimeException("Failed to delete slot with ID: " + slotId);
        }
    }
}
