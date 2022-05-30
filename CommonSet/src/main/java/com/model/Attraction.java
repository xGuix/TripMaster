package com.model;

import java.util.Objects;
import java.util.UUID;

public class Attraction {
    private String attractionName;
    private String city;
    private String state;
    private UUID attractionId;
    private double longitude;
    private double latitude;

    public Attraction() {
    }

    public Attraction(String attractionName, String city, String state, UUID attractionId, double longitude, double latitude) {
        this.attractionName = attractionName;
        this.city = city;
        this.state = state;
        this.attractionId = attractionId;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Attraction(String attractionName, String city, String state, UUID attractionId) {
        this.attractionName = attractionName;
        this.city = city;
        this.state = state;
        this.attractionId = attractionId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getAttractionName() {
        return attractionName;
    }

    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public UUID getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(UUID attractionId) {
        this.attractionId = attractionId;
    }

    @Override
    public String toString() {
        return "Attraction{" +
                "attractionName='" + attractionName + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", attractionId=" + attractionId +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attraction that = (Attraction) o;
        return Double.compare(that.longitude, longitude) == 0 && Double.compare(that.latitude, latitude) == 0
                && Objects.equals(attractionName, that.attractionName)
                && Objects.equals(city, that.city)
                && Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attractionName, city, state, attractionId, longitude, latitude);
    }
}