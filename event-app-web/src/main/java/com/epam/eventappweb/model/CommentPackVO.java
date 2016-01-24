package com.epam.eventappweb.model;


import java.util.List;

/**
 * class for representing Comments pack
 */
public class CommentPackVO {
    private List<CommentVO> commentVOList;
    private Integer remainingComments;

    public CommentPackVO(List<CommentVO> commentVOList, Integer remainingComments) {
        this.commentVOList = commentVOList;
        this.remainingComments = remainingComments;
    }

    public List<CommentVO> getCommentVOList() {
        return commentVOList;
    }

    public void setCommentVOList(List<CommentVO> commentVOList) {
        this.commentVOList = commentVOList;
    }

    public Integer getRemainingComments() {
        return remainingComments;
    }

    public void setRemainingComments(Integer remainingComments) {
        this.remainingComments = remainingComments;
    }

    @Override
    public String toString() {
        return "CommentPackVO{" +
                "commentVOList=" + commentVOList +
                ", remainingComments=" + remainingComments +
                '}';
    }
}
