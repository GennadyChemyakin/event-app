package com.epam.eventapp.service.model;

import com.epam.eventapp.service.domain.Comment;

import java.util.List;

/**
 * class for representing package of comments
 */
public final class CommentPack {
    private final List<Comment> comments;
    private final int remainingCommentsCount;

    public CommentPack(List<Comment> comments, int remainingCommentsCount) {
        this.comments = comments;
        this.remainingCommentsCount = remainingCommentsCount;
    }

    public List<Comment> getComments() {
        return comments;
    }


    public int getRemainingCommentsCount() {
        return remainingCommentsCount;
    }

    @Override
    public String toString() {
        return "CommentPack{" +
                "comments=" + comments +
                ", remainingCommentsCount=" + remainingCommentsCount +
                '}';
    }
}
