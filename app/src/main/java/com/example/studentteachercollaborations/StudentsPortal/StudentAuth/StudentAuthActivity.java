package com.example.studentteachercollaborations.StudentsPortal.StudentAuth;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.studentteachercollaborations.R;
import com.example.studentteachercollaborations.StudentsPortal.StudentNavigationDrawer;

public class StudentAuthActivity extends AppCompatActivity implements
        StudentLoginPage.StudentAuthListener,
        StudentLoginPage.StudentSignUpListener,
        StudentSignUp.OnAddStudentSuccessListener {
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_auth);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        StudentLoginPage studentLoginPage = new StudentLoginPage();
        ft.replace(R.id.studentFragmentContainerAuthentication, studentLoginPage);
        ft.commit();
    }

    @Override
    public void onStudentLoginSuccessful() {
        Intent intent = new Intent(this, StudentNavigationDrawer.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onStudentSignupClickSuccess() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        StudentSignUp studentSignUp = new StudentSignUp();
        ft.replace(R.id.studentFragmentContainerAuthentication, studentSignUp);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onAddStudentSuccessful() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        StudentLoginPage studentLoginPage = new StudentLoginPage();
        ft.replace(R.id.studentFragmentContainerAuthentication, studentLoginPage);
        ft.commit();
    }
}