package com.epam.eventappweb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jdk.nashorn.internal.ir.annotations.Immutable;

import java.time.LocalDateTime;

/**
 * class describes Event model for list of Events
 */
@Immutable
public class EventPreviewVO {
    private final String name;
    private final String creator;
    private final String description;
    private final String country;
    private final String city;
    private final String location;
    private final int numberOfComments;
    private final byte[] picture;
    private final LocalDateTime eventTime;
    private final LocalDateTime creationTime;

    private EventPreviewVO(EventPreviewModelBuilder builder) {
        this.name = builder.name;
        this.creator = builder.creator;
        this.description = builder.description;
        this.country = builder.country;
        this.city = builder.city;
        this.location = builder.location;
        this.numberOfComments = builder.numberOfComments;
        this.picture = builder.picture;
        this.eventTime = builder.eventTime;
        this.creationTime = builder.creationTime;
    }

    public static EventPreviewModelBuilder builder(String name){
        return new EventPreviewModelBuilder(name);
    }

    @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "")
    public static class EventPreviewModelBuilder {
        private String name;
        private String creator;
        private String description;
        private String country;
        private String city;
        private String location;
        private int numberOfComments;
        private byte[] picture;
        private LocalDateTime eventTime;
        private LocalDateTime creationTime;

        private EventPreviewModelBuilder(@JsonProperty("name") String name) {
            this.name = name;
        }

        public EventPreviewModelBuilder creator(String creator) {
            this.creator = creator;
            return this;
        }

        public EventPreviewModelBuilder description(String description) {
            this.description = description;
            return this;
        }

        public EventPreviewModelBuilder country(String country) {
            this.country = country;
            return this;
        }

        public EventPreviewModelBuilder city(String city) {
            this.city = city;
            return this;
        }

        public EventPreviewModelBuilder location(String location) {
            this.location = location;
            return this;
        }

        public EventPreviewModelBuilder numberOfComments(int numberOfComments) {
            this.numberOfComments = numberOfComments;
            return this;
        }

        public EventPreviewModelBuilder picture(byte[] picture) {
            this.picture = picture;
            return this;
        }


        public EventPreviewModelBuilder eventTime(LocalDateTime eventTime) {
            this.eventTime = eventTime;
            return this;
        }

        public EventPreviewModelBuilder creationTime(LocalDateTime creationTime) {
            this.creationTime = creationTime;
            return this;
        }

        public EventPreviewVO build() {
            return new EventPreviewVO(this);
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

    public int getNumberOfComments() {
        return numberOfComments;
    }

    public byte[] getPicture() {
        return picture;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    @Override
    public String toString() {
        return "EventPreviewVO{" +
                "creator=" + creator +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", location='" + location + '\'' +
                ", numberOfComments='" + numberOfComments + '\'' +
                ", location='" + location + '\'' +
                ", eventTime='" + eventTime + '\'' +
                ", creationTime=" + creationTime +
                '}';
    }

}
