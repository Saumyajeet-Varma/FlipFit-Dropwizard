package com.flipfit.dao;

import com.flipfit.entity.Slot;

import java.util.List;

public interface SlotDAO {

    Slot createSlot(int centerId, Slot slot);

    Slot getSlotById(int slotId);

    List<Slot> getSlotsByCenter(int centerId);

    List<Slot> getAvailableSlotsByCenter(int centerId);

    boolean isSlotAvailable(int slotId);

    boolean lockSlot(int slotId);

    boolean unlockSlot(int slotId);

    boolean deleteSlot(int slotId);
}
