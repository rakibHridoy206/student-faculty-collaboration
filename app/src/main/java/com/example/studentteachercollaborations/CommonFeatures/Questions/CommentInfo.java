package com.example.studentteachercollaborations.CommonFeatures.Questions;

public class CommentInfo {
    private String commentId;
    private String commentDescription;
    private String commentUserName;
    private String commentUserUrl;
    private String commentTime;

    public CommentInfo() {
    }

    public CommentInfo(String commentId, String commentDescription, String commentUserName, String commentUserUrl, String commentTime) {
        this.commentId = commentId;
        this.commentDescription = commentDescription;
        this.commentUserName = commentUserName;
        this.commentUserUrl = commentUserUrl;
        this.commentTime = commentTime;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentDescription() {
        return commentDescription;
    }

    public void setCommentDescription(String commentDescription) {
        this.commentDescription = commentDescription;
    }

    public String getCommentUserName() {
        return commentUserName;
    }

    public void setCommentUserName(String commentUserName) {
        this.commentUserName = commentUserName;
    }

    public String getCommentUserUrl() {
        return commentUserUrl;
    }

    public void setCommentUserUrl(String commentUserUrl) {
        this.commentUserUrl = commentUserUrl;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }
}
