package com.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.model.Location;

import java.util.UUID;

/**
 * User Location Model
 */
public class UserLocationDto {
    private UUID userId;
    private Location location;

    /**
     * Default empty constructor
     */
    public UserLocationDto() {
    }

    /**
     * Instantiates a new Users locations.
     *  @param uuid     the uuid
     * @param location the location
     */
    public UserLocationDto(UUID uuid, Location location) {
        this.userId = uuid;
        this.location = location;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public String getUserId() {
        return userId.toString();
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public Location getLocation() {
        return location;
    }
}