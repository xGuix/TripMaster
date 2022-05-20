package User.dto;

import gpsUtil.location.Location;

import java.util.UUID;

public class UserLocationDto {
    private final UUID userId;
    private final Location location;

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