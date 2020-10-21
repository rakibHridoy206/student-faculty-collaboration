package com.example.studentteachercollaborations;

public class ProjectInfo {
    private String projectID;
    private String projectName;
    private String projectDescription;
    private String projectLink;
    private String userID;

    public ProjectInfo() {
    }

    public ProjectInfo(String projectID, String projectName, String projectDescription, String projectLink, String userID) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectLink = projectLink;
        this.userID = userID;
    }

    public ProjectInfo(String projectID, String projectName, String projectDescription, String projectLink) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectLink = projectLink;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getProjectLink() {
        return projectLink;
    }

    public void setProjectLink(String projectLink) {
        this.projectLink = projectLink;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
