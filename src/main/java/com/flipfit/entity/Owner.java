package com.flipfit.entity;

import javax.validation.constraints.NotNull;
import java.util.List;

public class Owner extends User {

    @NotNull
    private boolean isVerified;

    private List<GymCenter> gymCenters;

    public Owner() {}

    public Owner(int userId, String name, String email, String password, boolean isVerified) {
        this.setUserId(userId);
        this.setName(name);
        this.setEmail(email);
        this.setPassword(password);
        this.isVerified = isVerified;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public List<GymCenter> getGymCenters() {
        return gymCenters;
    }

    public void setGymCenters(List<GymCenter> gymCenters) {
        this.gymCenters = gymCenters;
    }
}
