package com.epam.eventappweb.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDateTime;

/**
 * class for representing Commentary domain on presentation layer
 */
@JsonDeserialize(builder = CommentVO.CommentVOBuilder.class)
public final class CommentVO {

    private final int id;
    private final int eventId;
    private final String username;
    private final byte[] userPhoto;
    private final String message;
    private final LocalDateTime commentTime;

    private CommentVO(CommentVOBuilder builder){
        this.id = builder.id;
        this.eventId = builder.eventId;
        this.username = builder.username;
        this.userPhoto = builder.userPhoto;
        this.message = builder.message;
        this.commentTime = builder.commentTime;
    }

    public static CommentVOBuilder builder(){
        return new CommentVOBuilder();
    }

    @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "")
    public static class CommentVOBuilder {
        private int id;
        private int eventId;
        private String username;
        private byte[] userPhoto;
        private String message;
        private LocalDateTime commentTime;

        public CommentVOBuilder id(int id){
            this.id = id;
            return this;
        }

        public CommentVOBuilder eventId(int eventId){
            this.eventId = eventId;
            return this;
        }

        public CommentVOBuilder username(String username){
            this.username = username;
            return this;
        }

        public CommentVOBuilder userPhoto(byte[] userPhoto){
            this.userPhoto = userPhoto;
            return this;
        }

        public CommentVOBuilder message(String message){
            this.message = message;
            return this;
        }

        public CommentVOBuilder commentTime(LocalDateTime commentTime){
            this.commentTime = commentTime;
            return this;
        }

        public CommentVO build() {
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

    public LocalDateTime getCommentTime() {
        return commentTime;
    }

    @Override
    public String toString() {
        return "CommentVO{" +
                "id=" + id +
                ", eventId=" + eventId +
                ", username='" + username + '\'' +
                ", message='" + message + '\'' +
                ", commentTime=" + commentTime +
                '}';
    }
}
