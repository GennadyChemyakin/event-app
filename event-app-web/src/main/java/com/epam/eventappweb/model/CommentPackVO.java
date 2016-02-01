package com.epam.eventappweb.model;


import java.util.List;

/**
 * class for representing Comments pack
 */
public final class CommentPackVO {
    private final List<CommentVO> commentVOList;
    private final int remainingCommentsCount;

    public CommentPackVO(List<CommentVO> commentVOList, int remainingCommentsCount) {
        this.commentVOList = commentVOList;
        this.remainingCommentsCount = remainingCommentsCount;
    }

    public List<CommentVO> getCommentVOList() {
        return commentVOList;
    }

    public int getRemainingCommentsCount() {
        return remainingCommentsCount;
    }

    @Override
    public String toString() {
        return "CommentPackVO{" +
                "commentVOList=" + commentVOList +
                ", remainingCommentsCount=" + remainingCommentsCount +
                '}';
    }
}
