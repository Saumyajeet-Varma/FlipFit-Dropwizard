package com.flipfit.entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class GymCenter {

    @NotNull
    private int centerId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String location;

    @NotNull
    private boolean approved;

    @NotNull
    private int ownerId;

    private List<Slot> slots;

    public GymCenter() {}

    public GymCenter(int centerId, String name, String location, boolean approved, int ownerId) {
        this.centerId = centerId;
        this.name = name;
        this.location = location;
        this.approved = approved;
        this.ownerId = ownerId;
    }

    public int getCenterId() {
        return centerId;
    }

    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}
