package com.epam.eventappweb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalDateTime;

/**
 * class describes EVENT model
 * without collections for storing photo and video objects
 */
@JsonDeserialize(builder = EventVO.EventModelBuilder.class)
public final class EventVO {
    private final String name;
    private final String creator;
    private final String description;
    private final String country;
    private final String city;
    private final String location;
    private final double gpsLatitude;
    private final double gpsLongitude;
    private final LocalDateTime eventTime;

    private EventVO(EventModelBuilder builder) {
        this.name = builder.name;
        this.creator = builder.creator;
        this.description = builder.description;
        this.country = builder.country;
        this.city = builder.city;
        this.location = builder.location;
        this.gpsLatitude = builder.gpsLatitude;
        this.gpsLongitude = builder.gpsLongitude;
        this.eventTime = builder.eventTime;
    }

    public static EventModelBuilder builder(String name){
        return new EventModelBuilder(name);
    }

    @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "")
    public static class EventModelBuilder {
        private String name;
        private String creator;
        private String description;
        private String country;
        private String city;
        private String location;
        private double gpsLatitude;
        private double gpsLongitude;
        private LocalDateTime eventTime;

        private EventModelBuilder(@JsonProperty("name") String name) {
            this.name = name;
        }

        public EventModelBuilder creator(String creator) {
            this.creator = creator;
            return this;
        }

        public EventModelBuilder description(String description) {
            this.description = description;
            return this;
        }

        public EventModelBuilder country(String country) {
            this.country = country;
            return this;
        }

        public EventModelBuilder city(String city) {
            this.city = city;
            return this;
        }

        public EventModelBuilder location(String location) {
            this.location = location;
            return this;
        }

        public EventModelBuilder gpsLatitude(double gpsLatitude) {
            this.gpsLatitude = gpsLatitude;
            return this;
        }

        public EventModelBuilder gpsLongitude(double gpsLongitude) {
            this.gpsLongitude = gpsLongitude;
            return this;
        }

        public EventModelBuilder eventTime(LocalDateTime eventTime) {
            this.eventTime = eventTime;
            return this;
        }

        public EventVO build() {
            return new EventVO(this);
        }
    }

    public String getCreator() {
        return creator;
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

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    @Override
    public String toString() {
        return "EventVO{" +
                "creator=" + creator +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", location='" + location + '\'' +
                ", gpsLatitude=" + gpsLatitude +
                ", gpsLongitude=" + gpsLongitude +
                ", eventTime=" + eventTime +
                '}';
    }
}
