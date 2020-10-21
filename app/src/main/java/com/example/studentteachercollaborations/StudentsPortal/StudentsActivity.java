package com.example.studentteachercollaborations.StudentsPortal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.studentteachercollaborations.FacultyPanel.FacultyBooksShow;
import com.example.studentteachercollaborations.R;

public class StudentsActivity extends AppCompatActivity implements StudentLoginPage.StudentSignUpListener, StudentLoginPage.StudentAuthListener,
        StudentSignUp.OnAddStudentSuccessListener,
        StudentDashBoard.OnStudentsBooksClickListener, StudentsBooksSemesterList.OnStudentsSemesterNoClick {
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        StudentLoginPage studentLoginPage = new StudentLoginPage();
        ft.replace(R.id.fragmentContainerStudents, studentLoginPage);
        ft.commit();
    }

    @Override
    public void onStudentLoginSuccessful() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        StudentDashBoard studentDashBoard = new StudentDashBoard();
        ft.replace(R.id.fragmentContainerStudents, studentDashBoard);
        ft.commit();
    }

    @Override
    public void onStudentSignupClickSuccess() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        StudentSignUp studentSignUp = new StudentSignUp();
        ft.replace(R.id.fragmentContainerStudents, studentSignUp);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onAddStudentSuccessful() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        StudentLoginPage studentLoginPage = new StudentLoginPage();
        ft.replace(R.id.fragmentContainerStudents, studentLoginPage);
        ft.commit();
    }

    @Override
    public void onStudentsBooksClickSuccessful() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        StudentsBooksSemesterList studentsBooksSemesterList = new StudentsBooksSemesterList();
        ft.replace(R.id.fragmentContainerStudents, studentsBooksSemesterList);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onStudentsSemesterNoClickSuccessful(String semesterNo) {
        Bundle bundle = new Bundle();
        bundle.putString("key", semesterNo);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        StudentsBooksShow studentsBooksShow = new StudentsBooksShow();
        studentsBooksShow.setArguments(bundle);
        ft.replace(R.id.fragmentContainerStudents, studentsBooksShow);
        ft.addToBackStack(null);
        ft.commit();
    }
}