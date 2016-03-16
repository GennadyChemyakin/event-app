package com.epam.eventappweb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jdk.nashorn.internal.ir.annotations.Immutable;


import java.time.LocalDateTime;
import java.util.Optional;

/**
 * class describes EVENT model
 * without collections for storing photo and video objects
 */
@JsonDeserialize(builder = EventVO.EventModelBuilder.class)
public final class EventVO {

    private final String name;
    private final String creator;
    private final Optional<String> creatorName;
    private final Optional<String> creatorSurname;
    private final Optional<String> description;
    private final Optional<String> country;
    private final Optional<String> city;
    private final Optional<String> location;
    private final double gpsLatitude;
    private final double gpsLongitude;
    private final Optional<LocalDateTime> eventTime;
    private final int id;

    private EventVO(EventModelBuilder builder) {
        this.name = builder.name;
        this.creator = builder.creator;
        this.creatorName = Optional.ofNullable(builder.creatorName);
        this.creatorSurname = Optional.ofNullable(builder.creatorSurname);
        this.description = Optional.ofNullable(builder.description);
        this.country = Optional.ofNullable(builder.country);
        this.city = Optional.ofNullable(builder.city);
        this.location = Optional.ofNullable(builder.location);
        this.gpsLatitude = builder.gpsLatitude;
        this.gpsLongitude = builder.gpsLongitude;
        this.eventTime = Optional.ofNullable(builder.eventTime);
        this.id = builder.id;
    }

    public static EventModelBuilder builder(String name) {
        return new EventModelBuilder(name);
    }

    @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "")
    public static class EventModelBuilder {

        private String name;
        private String creator;
        private String creatorName;
        private String creatorSurname;
        private String description;
        private String country;
        private String city;
        private String location;
        private double gpsLatitude;
        private double gpsLongitude;
        private LocalDateTime eventTime;
        private int id;

        private EventModelBuilder(@JsonProperty("name") String name) {
            this.name = name;
        }

        public EventModelBuilder creator(String creator) {
            this.creator = creator;
            return this;
        }

        public EventModelBuilder creatorName(String creatorName) {
            this.creatorName = creatorName;
            return this;
        }

        public EventModelBuilder creatorSurname(String creatorSurname) {
            this.creatorSurname = creatorSurname;
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

        public EventModelBuilder id(int id) {
            this.id = id;
            return this;
        }

        public EventVO build() {
            return new EventVO(this);
        }
    }

    public String getCreator() {
        return creator;
    }

    public Optional<String> getCreatorName() {
        return creatorName;
    }

    public Optional<String> getCreatorSurname() {
        return creatorSurname;
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

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "EventVO{" +
                "creator=" + creator + '\'' +
                ", creatorName=" + creatorName + '\'' +
                ", creatorSurname=" + creatorSurname + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", location='" + location + '\'' +
                ", gpsLatitude=" + gpsLatitude +
                ", gpsLongitude=" + gpsLongitude +
                ", timeStamp=" + eventTime +
                ", id = " + id + " }";
    }
}
