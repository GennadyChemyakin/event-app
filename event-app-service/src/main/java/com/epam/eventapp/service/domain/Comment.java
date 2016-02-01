package com.epam.eventapp.service.domain;

import java.time.LocalDateTime;

/**
 * class describes COMMENTARY domain
 */
public class Comment {
    private final int id;
    private final int eventId;
    private final User user;
    private final LocalDateTime timeStamp;
    private final String message;

    private Comment(CommentBuilder commentBuilder) {
        this.id = commentBuilder.id;
        this.eventId = commentBuilder.eventId;
        this.user = commentBuilder.user;
        this.timeStamp = commentBuilder.timeStamp;
        this.message = commentBuilder.message;
    }

    public static CommentBuilder builder() {
        return new CommentBuilder();
    }

    public static class CommentBuilder {
        private int id;
        private int eventId;
        private User user;
        private LocalDateTime timeStamp;
        private String message;

        public CommentBuilder id(int id) {
            this.id = id;
            return this;
        }

        public CommentBuilder eventId(int eventId) {
            this.eventId = eventId;
            return this;
        }

        public CommentBuilder user(User user) {
            this.user = user;
            return this;
        }

        public CommentBuilder timeStamp(LocalDateTime timeStamp) {
            this.timeStamp = timeStamp;
            return this;
        }

        public CommentBuilder message(String message) {
            this.message = message;
            return this;
        }

        public Comment build(){
            return new Comment(this);
        }

    }

    public int getId() {
        return id;
    }

    public int getEventId() {
        return eventId;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", eventId=" + eventId +
                ", user=" + user +
                ", timeStamp=" + timeStamp +
                ", message='" + message + '\'' +
                '}';
    }
}
