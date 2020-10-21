package com.example.studentteachercollaborations.StudentsPortal;

public class StudentInfo {
    private String studentUserId;
    private String studentName;
    private String studentEmail;
    private String studentPhone;
    private String studentIntake;
    private String studentId;
    private String studentPassword;

    public StudentInfo() {
    }

    public StudentInfo(String studentUserId, String studentName, String studentEmail, String studentPhone, String studentIntake, String studentId, String studentPassword) {
        this.studentUserId = studentUserId;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.studentPhone = studentPhone;
        this.studentIntake = studentIntake;
        this.studentId = studentId;
        this.studentPassword = studentPassword;
    }

    public String getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(String studentUserId) {
        this.studentUserId = studentUserId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }

    public String getStudentIntake() {
        return studentIntake;
    }

    public void setStudentIntake(String studentIntake) {
        this.studentIntake = studentIntake;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }
}
