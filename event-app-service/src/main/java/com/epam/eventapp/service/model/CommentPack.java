package com.epam.eventapp.service.model;

import com.epam.eventapp.service.domain.Comment;

import java.util.List;

/**
 * class for representing package of comments
 */
public class CommentPack {
    private List<Comment> comments;
    private Integer remainingComments;

    public CommentPack(List<Comment> comments, Integer remainingComments) {
        this.comments = comments;
        this.remainingComments = remainingComments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Integer getRemainingComments() {
        return remainingComments;
    }

    public void setRemainingComments(Integer remainingComments) {
        this.remainingComments = remainingComments;
    }

    @Override
    public String toString() {
        return "CommentPack{" +
                "comments=" + comments +
                ", remainingComments=" + remainingComments +
                '}';
    }
}
