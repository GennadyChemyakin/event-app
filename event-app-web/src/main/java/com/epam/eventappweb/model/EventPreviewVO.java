package com.epam.eventappweb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * class describes Event model for list of Events
 */
public final class EventPreviewVO {
    private final int id;
    private final String name;
    private final String creator;
    private final Optional<String> description;
    private final Optional<String> country;
    private final Optional<String> city;
    private final Optional<String> location;
    private final int numberOfComments;
    private final Optional<byte[]> picture;
    private final Optional<LocalDateTime> eventTime;
    private final LocalDateTime createTime;

    private EventPreviewVO(EventPreviewModelBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.creator = builder.creator;
        this.description = Optional.ofNullable(builder.description);
        this.country = Optional.ofNullable(builder.country);
        this.city = Optional.ofNullable(builder.city);
        this.location = Optional.ofNullable(builder.location);
        this.numberOfComments = builder.numberOfComments;
        this.picture = Optional.ofNullable(builder.picture);
        this.eventTime = Optional.ofNullable(builder.eventTime);
        this.createTime = builder.createTime;
    }

    public static EventPreviewModelBuilder builder(int id) {
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

    public int getNumberOfComments() {
        return numberOfComments;
    }

    public Optional<byte[]> getPicture() {
        return picture;
    }

    public Optional<LocalDateTime> getEventTime() {
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
