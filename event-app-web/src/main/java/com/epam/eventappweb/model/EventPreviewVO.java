package com.epam.eventappweb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalDateTime;

/**
 * class describes Event model for list of Events
 */
public final class EventPreviewVO {
    private final int id;
    private final String name;
    private final String creator;
    private final String description;
    private final String country;
    private final String city;
    private final String location;
    private final int numberOfComments;
    private final byte[] picture;
    private final LocalDateTime eventTime;
    private final LocalDateTime createTime;

    private EventPreviewVO(EventPreviewModelBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.creator = builder.creator;
        this.description = builder.description;
        this.country = builder.country;
        this.city = builder.city;
        this.location = builder.location;
        this.numberOfComments = builder.numberOfComments;
        this.picture = builder.picture;
        this.eventTime = builder.eventTime;
        this.createTime = builder.createTime;
    }

    public static EventPreviewModelBuilder builder(int id){
        return new EventPreviewModelBuilder(id);
    }

    @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "")
    public static class EventPreviewModelBuilder {
        private int id;
        private String name;
        private String creator;
        private String description;
        private String country;
        private String city;
        private String location;
        private int numberOfComments;
        private byte[] picture;
        private LocalDateTime eventTime;
        private LocalDateTime createTime;

        private EventPreviewModelBuilder(@JsonProperty("id") int id) {
            this.id = id;
        }

        public EventPreviewModelBuilder name(String name) {
            this.name = name;
            return this;
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

        public EventPreviewModelBuilder creationTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public EventPreviewVO build() {
            return new EventPreviewVO(this);
        }
    }

    public int getId() {
        return id;
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    @Override
    public String toString() {
        return "EventPreviewVO{" +
                "id=" + id +
                "creator'=" + creator + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", location='" + location + '\'' +
                ", numberOfComments='" + numberOfComments + '\'' +
                ", location='" + location + '\'' +
                ", eventTime='" + eventTime + '\'' +
                ", createTime=" + createTime +
                '}';
    }

}
