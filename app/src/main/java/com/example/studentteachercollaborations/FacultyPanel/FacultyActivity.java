package com.example.studentteachercollaborations.FacultyPanel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.studentteachercollaborations.AddProject;
import com.example.studentteachercollaborations.R;

public class FacultyActivity extends AppCompatActivity implements FacultyLoginPage.FacultyAuthListener, FacultyLoginPage.FacultySignUpListener,
        FacultySignUp.OnAddFacultySuccessListener, FacultyDashBoard.OnBooksClickListener, FacultySemesterList.OnFacultySemesterNoClick,
        FacultyDashBoard.OnProjectClickListener, ProjectFaculty.OnAddProjectClick, AddProject.OnProjectAdded {
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);
        fragmentManager = getSupportFragmentManager();

            FragmentTransaction ft = fragmentManager.beginTransaction();
            FacultyLoginPage facultyLoginPage = new FacultyLoginPage();
            ft.add(R.id.fragmentContainerFaculty, facultyLoginPage);
            ft.commit();

    }

    @Override
    public void onFacultyLoginSuccessful() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        FacultyDashBoard facultyDashBoard = new FacultyDashBoard();
        ft.replace(R.id.fragmentContainerFaculty, facultyDashBoard);
        ft.commit();
    }

    @Override
    public void onFacultySignupClickSuccess() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        FacultySignUp facultySignUp = new FacultySignUp();
        ft.replace(R.id.fragmentContainerFaculty, facultySignUp);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onAddFacultySuccessfull() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        FacultyLoginPage facultyLoginPage = new FacultyLoginPage();
        ft.replace(R.id.fragmentContainerFaculty, facultyLoginPage);
        ft.commit();
    }

    @Override
    public void onBooksClickSuccessful() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        FacultySemesterList facultySemesterList = new FacultySemesterList();
        ft.replace(R.id.fragmentContainerFaculty, facultySemesterList);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onFacultySemesterNoClickSuccessful(String semesterNo) {
        Bundle bundle = new Bundle();
        bundle.putString("key", semesterNo);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        FacultyBooksShow facultyBooksShow = new FacultyBooksShow();
        facultyBooksShow.setArguments(bundle);
        ft.replace(R.id.fragmentContainerFaculty, facultyBooksShow);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onProjectClickSuccessful() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ProjectFaculty projectFaculty = new ProjectFaculty();
        ft.replace(R.id.fragmentContainerFaculty, projectFaculty);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onAddProjectClickSuccessful(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("key", id);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        AddProject addProject = new AddProject();
        addProject.setArguments(bundle);
        ft.replace(R.id.fragmentContainerFaculty, addProject);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onProjectAddedSuccessful() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ProjectFaculty projectFaculty = new ProjectFaculty();
        ft.replace(R.id.fragmentContainerFaculty, projectFaculty);
        ft.commit();
    }
}