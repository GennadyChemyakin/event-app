package com.epam.eventapp.service.domain;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * class describes EVENT domain
 * without collections for storing photo and video objects
 */
public class Event {
    private final int id;
    private final User user;
    private final String name;
    private final Optional<String> description;
    private final Optional<String> country;
    private final Optional<String> city;
    private final Optional<String> location;
    private final double gpsLatitude;
    private final double gpsLongitude;
    private final Optional<LocalDateTime> eventTime;
    private final LocalDateTime creationTime;

    private Event(EventBuilder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.name = builder.name;
        this.description = Optional.ofNullable(builder.description);
        this.country = Optional.ofNullable(builder.country);
        this.city = Optional.ofNullable(builder.city);
        this.location = Optional.ofNullable(builder.location);
        this.gpsLatitude = builder.gpsLatitude;
        this.gpsLongitude = builder.gpsLongitude;
        this.eventTime = Optional.ofNullable(builder.eventTime);
        this.creationTime = builder.creationTime;
    }

    public static EventBuilder builder(String name) {
        return new EventBuilder(name);
    }

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
        private LocalDateTime eventTime;
        private LocalDateTime creationTime;

        private EventBuilder(String name) {
            this.name = name;
        }

        public EventBuilder user(User user) {
            this.user = user;
            return this;
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

        public EventBuilder eventTime(LocalDateTime eventTime) {
            this.eventTime = eventTime;
            return this;
        }

        public EventBuilder creationTime(LocalDateTime creationTime) {
            this.creationTime = creationTime;
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

    public Optional<String> getDescription() {
        return description;
    }

    public Optional<String> getCountry() {
        return country;
    }

    public Optional<String> getCity() {
        return city;
    }

    public Optional<String> getLocation() {
        return location;
    }

    public double getGpsLatitude() {
        return gpsLatitude;
    }

    public double getGpsLongitude() {
        return gpsLongitude;
    }

    public Optional<LocalDateTime> getEventTime() {
        return eventTime;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", user=" + user +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", location='" + location + '\'' +
                ", gpsLatitude=" + gpsLatitude +
                ", gpsLongitude=" + gpsLongitude +
                ", eventTime=" + eventTime +
                ", creationTime=" + creationTime +
                '}';
    }
}
