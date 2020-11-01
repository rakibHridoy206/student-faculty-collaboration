package com.example.studentteachercollaborations.CommonFeatures.Project;

public class ProjectInfo {
    private String projectID;
    private String projectName;
    private String projectDescription;
    private String projectLink;
    private String userName;
    private String userDesignation;

    public ProjectInfo() {
    }

    public ProjectInfo(String projectID, String projectName, String projectDescription, String projectLink, String userName, String userDesignation) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectLink = projectLink;
        this.userName = userName;
        this.userDesignation = userDesignation;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDesignation() {
        return userDesignation;
    }

    public void setUserDesignation(String userDesignation) {
        this.userDesignation = userDesignation;
    }
}
