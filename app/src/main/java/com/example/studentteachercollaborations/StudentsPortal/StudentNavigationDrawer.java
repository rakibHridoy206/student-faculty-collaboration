package com.example.studentteachercollaborations.StudentsPortal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.studentteachercollaborations.CommonFeatures.Helpline;
import com.example.studentteachercollaborations.CommonFeatures.Project.AddProject;
import com.example.studentteachercollaborations.CommonFeatures.Thesis.AddThesis;
import com.example.studentteachercollaborations.FacultyPanel.FacultyAuth.FacultyAuthActivity;
import com.example.studentteachercollaborations.CommonFeatures.NoticeBoard;
import com.example.studentteachercollaborations.R;
import com.example.studentteachercollaborations.CommonFeatures.Thesis.ThesisFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class StudentNavigationDrawer extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        StudentDashBoard.OnStudentsBooksClickListener,
        StudentDashBoard.OnStudentHelplineClickListener,
        StudentDashBoard.OnStudentNoticeClickListener,
        StudentDashBoard.OnStudentProjectClickListener,
        StudentDashBoard.OnStudentQuestionsClickListener,
        StudentDashBoard.OnStudentThesisClickListener,

        StudentsBooksSemesterList.OnStudentsSemesterNoClick,

        ThesisFragment.OnAddThesisClick,
        AddThesis.OnThesisPaperAddClick,

        ProjectStudents.OnStudentAddProjectClick,
        AddProject.OnProjectAdded,

        StudentsQuestions.OnAskedQuestionStudentListener {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_navigation_drawer);

        Toolbar toolbar = findViewById(R.id.toolbarStudent);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.student_drawer_layout);
        NavigationView navigationView = findViewById(R.id.student_nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        StudentDashBoard dashBoard = new StudentDashBoard();
        ft.add(R.id.studentFragmentContainerMain, dashBoard);
        ft.commit();

        LinearLayout linearLayout = findViewById(R.id.logoutLinearStudent);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentNavigationDrawer.this, FacultyAuthActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                snackbarShow();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.nav_profileStudent:
                break;
            case R.id.navAnnexStudent:
                break;
        }

        DrawerLayout drawer = findViewById(R.id.student_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void snackbarShow(){
        Snackbar make = Snackbar.make(this.findViewById(android.R.id.content), "Successfully logged out", Snackbar.LENGTH_LONG);
        View snackbarLayout = make.getView();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        lp.setMargins(200, 700, 200, 0);
        snackbarLayout.setLayoutParams(lp);
        snackbarLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorLightBlue));
        make.show();
    }


    /// DASHBOARD ITEM CLICKS
    /// books click
    @Override
    public void onStudentsBooksClickSuccessful() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        StudentsBooksSemesterList studentsBooksSemesterList = new StudentsBooksSemesterList();
        ft.replace(R.id.studentFragmentContainerMain, studentsBooksSemesterList);
        ft.addToBackStack(null);
        ft.commit();
    }
    /// project click
    @Override
    public void onStudentProjectClickSuccessful() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ProjectStudents projectStudents = new ProjectStudents();
        ft.replace(R.id.studentFragmentContainerMain, projectStudents);
        ft.addToBackStack(null);
        ft.commit();
    }
    /// thesis click
    @Override
    public void studentThesisClickSuccessful() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ThesisFragment thesisFragment = new ThesisFragment();
        ft.replace(R.id.studentFragmentContainerMain, thesisFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
    /// question click
    @Override
    public void studentQuestionsClickSuccessful() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        StudentsQuestions studentsQuestions = new StudentsQuestions();
        ft.replace(R.id.studentFragmentContainerMain, studentsQuestions);
        ft.addToBackStack(null);
        ft.commit();
    }
    /// notice click
    @Override
    public void studentNoticeClickSuccessful() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        NoticeBoard noticeBoard = new NoticeBoard();
        ft.replace(R.id.studentFragmentContainerMain, noticeBoard);
        ft.addToBackStack(null);
        ft.commit();
    }
    /// helpline click
    @Override
    public void studentHelplineClickSuccessful() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Helpline helpline = new Helpline();
        ft.replace(R.id.studentFragmentContainerMain, helpline);
        ft.addToBackStack(null);
        ft.commit();
    }


    /// student semester no click successful
    @Override
    public void onStudentsSemesterNoClickSuccessful(String semesterNo) {
        Bundle bundle = new Bundle();
        bundle.putString("key", semesterNo);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        StudentsBooksShow studentsBooksShow = new StudentsBooksShow();
        studentsBooksShow.setArguments(bundle);
        ft.replace(R.id.studentFragmentContainerMain, studentsBooksShow);
        ft.addToBackStack(null);
        ft.commit();
    }


    // thesis section
    @Override
    public void onAddThesisSuccessful() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        AddThesis addThesis = new AddThesis();
        ft.replace(R.id.studentFragmentContainerMain, addThesis);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void thesisPaperAddSuccessful() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ThesisFragment thesisFragment = new ThesisFragment();
        ft.replace(R.id.studentFragmentContainerMain, thesisFragment);
        ft.commit();
    }


    /// project section
    @Override
    public void studentAddProjectClickSuccessful(String name, String designation) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("des", designation);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        AddProject addProject = new AddProject();
        addProject.setArguments(bundle);
        ft.replace(R.id.studentFragmentContainerMain, addProject);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onProjectAddedSuccessful() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ProjectStudents projectStudents = new ProjectStudents();
        ft.replace(R.id.studentFragmentContainerMain, projectStudents);
        ft.commit();
    }

    @Override
    public void askedQuestionStudentSuccessful(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        AnswerPageStudent answerPageStudent = new AnswerPageStudent();
        answerPageStudent.setArguments(bundle);
        ft.replace(R.id.studentFragmentContainerMain, answerPageStudent);
        ft.addToBackStack(null);
        ft.commit();
    }
}