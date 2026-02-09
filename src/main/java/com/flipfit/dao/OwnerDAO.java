package com.flipfit.dao;

import com.flipfit.entity.Owner;

import java.util.List;

public interface OwnerDAO {

    boolean addOwner(Owner owner);

    Owner getOwnerById(int ownerId);

    List<Owner> getAllOwners();
}
