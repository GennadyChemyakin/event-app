package com.epam.eventapp.service.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalDateTime;

/**
 * class describes EVENT domain
 * without collections for storing photo and video objects
 */
@JsonDeserialize(builder = Event.EventBuilder.class)
public class Event {
    private final int id;
    private final User user;
    private final String name;
    private final String description;
    private final String country;
    private final String city;
    private final String location;
    private final double gpsLatitude;
    private final double gpsLongitude;
    private final LocalDateTime timeStamp;

    private Event(EventBuilder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.name = builder.name;
        this.description = builder.description;
        this.country = builder.country;
        this.city = builder.city;
        this.location = builder.location;
        this.gpsLatitude = builder.gpsLatitude;
        this.gpsLongitude = builder.gpsLongitude;
        this.timeStamp = builder.timeStamp;
    }

    public static EventBuilder builder(User user, String name){
        return new EventBuilder(user,name);
    }

    @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "")
    public static class EventBuilder {
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

        private EventBuilder(@JsonProperty("user") User user, @JsonProperty("name") String name) {
            this.user = user;
            this.name = name;
        }

        public EventBuilder id(int id) {
            this.id = id;
            return this;
        }

        public EventBuilder description(String description) {
            this.description = description;
            return this;
        }

        public EventBuilder country(String country) {
            this.country = country;
            return this;
        }

        public EventBuilder city(String city) {
            this.city = city;
            return this;
        }

        public EventBuilder location(String location) {
            this.location = location;
            return this;
        }

        public EventBuilder gpsLatitude(double gpsLatitude) {
            this.gpsLatitude = gpsLatitude;
            return this;
        }

        public EventBuilder gpsLongitude(double gpsLongitude) {
            this.gpsLongitude = gpsLongitude;
            return this;
        }

        public EventBuilder timeStamp(LocalDateTime timeStamp) {
            this.timeStamp = timeStamp;
            return this;
        }

        public Event build() {
            return new Event(this);
        }
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getLocation() {
        return location;
    }

    public double getGpsLatitude() {
        return gpsLatitude;
    }

    public double getGpsLongitude() {
        return gpsLongitude;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
}
