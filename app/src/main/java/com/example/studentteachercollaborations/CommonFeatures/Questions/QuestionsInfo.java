package com.example.studentteachercollaborations.CommonFeatures.Questions;

public class QuestionsInfo {
    private String questionID;
    private String questionTitle;
    private String questionDescription;
    private String questionUserName;
    private String questionUserUrl;
    private String questionTime;

    public QuestionsInfo() {
    }

    public QuestionsInfo(String questionID, String questionTitle, String questionDescription, String questionUserName, String questionUserUrl, String questionTime) {
        this.questionID = questionID;
        this.questionTitle = questionTitle;
        this.questionDescription = questionDescription;
        this.questionUserName = questionUserName;
        this.questionUserUrl = questionUserUrl;
        this.questionTime = questionTime;
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }

    public String getQuestionUserName() {
        return questionUserName;
    }

    public void setQuestionUserName(String questionUserName) {
        this.questionUserName = questionUserName;
    }

    public String getQuestionUserUrl() {
        return questionUserUrl;
    }

    public void setQuestionUserUrl(String questionUserUrl) {
        this.questionUserUrl = questionUserUrl;
    }

    public String getQuestionTime() {
        return questionTime;
    }

    public void setQuestionTime(String questionTime) {
        this.questionTime = questionTime;
    }
}
