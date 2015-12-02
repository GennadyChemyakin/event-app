package com.epam.eventappservices.domain;

import java.time.LocalDateTime;

/**
 * domain class describes EVENT table
 * without collections for storing photo and video objects
 */
public class Event {
    private int id;
    private User user;
    private String name;
    private String description;
    private String country;
    private String city;
    private String location;
    private double gpsLatitude;
    private double gpsLongitude;
    private LocalDateTime timeStamp;

    public Event(int id, User user, String name, String description, String country, String city, String location,
                 double gpsLatitude, double gpsLongitude, LocalDateTime timeStamp) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.description = description;
        this.country = country;
        this.city = city;
        this.location = location;
        this.gpsLatitude = gpsLatitude;
        this.gpsLongitude = gpsLongitude;
        this.timeStamp = timeStamp;
    }

    public Event() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getGpsLatitude() {
        return gpsLatitude;
    }

    public void setGpsLatitude(double gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
    }

    public double getGpsLongitude() {
        return gpsLongitude;
    }

    public void setGpsLongitude(double gpsLongitude) {
        this.gpsLongitude = gpsLongitude;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
