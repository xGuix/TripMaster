package com.model;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class VisitedLocation {
    private UUID userId;
    private Location location;
    private Date visitedTime;

    public VisitedLocation(gpsUtil.location.VisitedLocation visitedLocation) {
    }

    public VisitedLocation(UUID userId, Location location, Date visitedTime) {
        this.userId = userId;
        this.location = location;
        this.visitedTime = visitedTime;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Date getVisitedTime() {
        return visitedTime;
    }

    public void setVisitedTime(Date visitedTime) {
        this.visitedTime = visitedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisitedLocation that = (VisitedLocation) o;
        return Objects.equals(userId, that.userId)
                && Objects.equals(location, that.location)
                && Objects.equals(visitedTime, that.visitedTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, location, visitedTime);
    }

    @Override
    public String toString() {
        return "VisitedLocation{" +
                "userId=" + userId +
                ", location=" + location +
                ", timeVisited=" + visitedTime +
                '}';
    }
}