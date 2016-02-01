package com.epam.eventappweb.model;

import java.time.LocalDateTime;

/**
 * class for representing Commentary domain on presentation layer
 */
public final class CommentVO {

    private final int id;
    private final int eventId;
    private final String username;
    private final byte[] userPhoto;
    private final String message;
    private final LocalDateTime timeStamp;

    private CommentVO(CommentViewBuilder builder){
        this.id = builder.id;
        this.eventId = builder.eventId;
        this.username = builder.username;
        this.userPhoto = builder.userPhoto;
        this.message = builder.message;
        this.timeStamp = builder.timeStamp;
    }

    public static CommentViewBuilder builder(){
        return new CommentViewBuilder();
    }

    public static class CommentViewBuilder{
        private int id;
        private int eventId;
        private String username;
        private byte[] userPhoto;
        private String message;
        private LocalDateTime timeStamp;

        public CommentViewBuilder id(int id){
            this.id = id;
            return this;
        }

        public CommentViewBuilder eventId(int eventId){
            this.eventId = eventId;
            return this;
        }

        public CommentViewBuilder username(String username){
            this.username = username;
            return this;
        }

        public CommentViewBuilder userPhoto(byte[] userPhoto){
            this.userPhoto = userPhoto;
            return this;
        }

        public CommentViewBuilder message(String message){
            this.message = message;
            return this;
        }

        public CommentViewBuilder timeStamp(LocalDateTime timeStamp){
            this.timeStamp = timeStamp;
            return this;
        }

        public CommentVO build(){
            return new CommentVO(this);
        }
    }

    public int getId() {
        return id;
    }

    public int getEventId() {
        return eventId;
    }

    public String getUsername() {
        return username;
    }

    public byte[] getUserPhoto() {
        return userPhoto;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    @Override
    public String toString() {
        return "CommentVO{" +
                "id=" + id +
                ", eventId=" + eventId +
                ", username='" + username + '\'' +
                ", message='" + message + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
