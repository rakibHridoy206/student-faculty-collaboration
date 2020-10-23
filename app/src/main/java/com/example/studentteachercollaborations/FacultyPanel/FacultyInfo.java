package com.example.studentteachercollaborations.FacultyPanel;

public class FacultyInfo {
    private String facultyId;
    private String facultyName;
    private String facultyEmail;
    private String facultyPhone;
    private String facultyDesignation;
    private String facultyPassword;
    private String facultyImageUrl;

    public FacultyInfo() {
    }

    public FacultyInfo(String facultyId, String facultyName, String facultyEmail, String facultyPhone, String facultyDesignation, String facultyPassword, String facultyImageUrl) {
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.facultyEmail = facultyEmail;
        this.facultyPhone = facultyPhone;
        this.facultyDesignation = facultyDesignation;
        this.facultyPassword = facultyPassword;
        this.facultyImageUrl = facultyImageUrl;
    }

    public FacultyInfo(String facultyId, String facultyName, String facultyEmail, String facultyPhone, String facultyDesignation, String facultyPassword) {
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.facultyEmail = facultyEmail;
        this.facultyPhone = facultyPhone;
        this.facultyDesignation = facultyDesignation;
        this.facultyPassword = facultyPassword;
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getFacultyEmail() {
        return facultyEmail;
    }

    public void setFacultyEmail(String facultyEmail) {
        this.facultyEmail = facultyEmail;
    }

    public String getFacultyPhone() {
        return facultyPhone;
    }

    public void setFacultyPhone(String facultyPhone) {
        this.facultyPhone = facultyPhone;
    }

    public String getFacultyDesignation() {
        return facultyDesignation;
    }

    public void setFacultyDesignation(String facultyDesignation) {
        this.facultyDesignation = facultyDesignation;
    }

    public String getFacultyPassword() {
        return facultyPassword;
    }

    public void setFacultyPassword(String facultyPassword) {
        this.facultyPassword = facultyPassword;
    }

    public String getFacultyImageUrl() {
        return facultyImageUrl;
    }

    public void setFacultyImageUrl(String facultyImageUrl) {
        this.facultyImageUrl = facultyImageUrl;
    }
}
