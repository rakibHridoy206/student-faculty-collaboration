package com.example.studentteachercollaborations.FacultyPanel.FacultyAuth;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.studentteachercollaborations.FacultyPanel.FacultyNavigationDrawer;
import com.example.studentteachercollaborations.R;

public class FacultyAuthActivity extends AppCompatActivity implements
        FacultyLoginPage.FacultyAuthListener,
        FacultyLoginPage.FacultySignUpListener,

        FacultySignUp.OnAddFacultySuccessListener {
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_auth);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        FacultyLoginPage facultyLoginPage = new FacultyLoginPage();
        ft.add(R.id.fragmentContainerFacultyAuth, facultyLoginPage);
        ft.commit();
    }

    @Override
    public void onFacultyLoginSuccessful() {
        Intent intent = new Intent(this, FacultyNavigationDrawer.class);
       // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFacultySignupClickSuccess() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        FacultySignUp facultySignUp = new FacultySignUp();
        ft.replace(R.id.fragmentContainerFacultyAuth, facultySignUp);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onAddFacultySuccessfull() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        FacultyLoginPage facultyLoginPage = new FacultyLoginPage();
        ft.replace(R.id.fragmentContainerFacultyAuth, facultyLoginPage);
        ft.commit();
    }
}