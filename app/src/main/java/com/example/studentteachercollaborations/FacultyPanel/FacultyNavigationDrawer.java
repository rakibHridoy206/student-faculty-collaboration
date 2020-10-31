package com.example.studentteachercollaborations.FacultyPanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.studentteachercollaborations.AddProject;
import com.example.studentteachercollaborations.AddThesis;
import com.example.studentteachercollaborations.FacultyPanel.FacultyAuth.FacultyAuthActivity;
import com.example.studentteachercollaborations.Helpline;
import com.example.studentteachercollaborations.NoticeBoard;
import com.example.studentteachercollaborations.R;
import com.example.studentteachercollaborations.ThesisFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class FacultyNavigationDrawer extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        FacultyDashBoard.OnBooksClickListener, FacultyDashBoard.OnProjectClickListener, FacultyDashBoard.OnNoticeClickListener,
        FacultyDashBoard.OnThesisClickListener, FacultyDashBoard.OnHelplineClickListener,
        FacultyBooksSemesterList.OnFacultySemesterClick,
        ProjectFaculty.OnAddProjectClick, AddProject.OnProjectAdded,
        ThesisFragment.OnAddThesisClick, AddThesis.OnThesisPaperAddClick {

    private AppBarConfiguration mAppBarConfiguration;
    private FragmentManager fragmentManager;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_navigation_drawer);
        Toolbar toolbar = findViewById(R.id.toolbarFaculty);
        imageView = findViewById(R.id.profileShowNav);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("FACULTY_INFO");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        /*if (currentUser != null){
            String uid = currentUser.getUid();

            databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Object link = snapshot.child("facultyImageUrl").getValue();
                    String url = String.valueOf(link);
                    Glide.with(FacultyNavigationDrawer.this).load(url).into(imageView);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }*/
        //Toast.makeText(FacultyNavigationDrawer.this, url[0], Toast.LENGTH_SHORT).show();
//        Glide.with(this).load(url[0]).into(imageView);




        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        FacultyDashBoard dashBoard = new FacultyDashBoard();
        ft.add(R.id.facultyFragmentContainer, dashBoard);
        ft.commit();

        LinearLayout linearLayout = findViewById(R.id.logoutLinear);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FacultyNavigationDrawer.this, FacultyAuthActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                snackbarShow();
            }
        });

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.nav_profile:
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // DASHBOARD FRAGMENT
    // books selected
    @Override
    public void onBooksClickSuccessful() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        FacultyBooksSemesterList booksSemesterList = new FacultyBooksSemesterList();
        ft.replace(R.id.facultyFragmentContainer, booksSemesterList);
        ft.addToBackStack(null);
        ft.commit();
    }

    // project selected
    @Override
    public void onProjectClickSuccessful() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ProjectFaculty projectFaculty = new ProjectFaculty();
        ft.replace(R.id.facultyFragmentContainer, projectFaculty);
        ft.addToBackStack(null);
        ft.commit();
    }

    // notice click
    @Override
    public void noticeClickSuccessful() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        NoticeBoard noticeBoard = new NoticeBoard();
        ft.replace(R.id.facultyFragmentContainer, noticeBoard);
        ft.addToBackStack(null);
        ft.commit();
    }

    // thesis click
    @Override
    public void onThesisClickSuccessful() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ThesisFragment thesisFragment = new ThesisFragment();
        ft.replace(R.id.facultyFragmentContainer, thesisFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    // helpline click
    @Override
    public void onHelplineClickSuccessful() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Helpline helpline = new Helpline();
        ft.replace(R.id.facultyFragmentContainer, helpline);
        ft.addToBackStack(null);
        ft.commit();
    }




    // BOOKS SEMESTER FRAGMENT
    // semester no click
    @Override
    public void onFacultySemesterClickSuccessful(String semesterNo) {
        Bundle bundle = new Bundle();
        bundle.putString("key", semesterNo);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        FacultyBooksShow facultyBooksShow = new FacultyBooksShow();
        facultyBooksShow.setArguments(bundle);
        ft.replace(R.id.facultyFragmentContainer, facultyBooksShow);
        ft.addToBackStack(null);
        ft.commit();
    }


    // UNDER PROJECT SECTION
    // add project button click
    @Override
    public void onAddProjectClickSuccessful(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("key", id);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        AddProject addProject = new AddProject();
        addProject.setArguments(bundle);
        ft.replace(R.id.facultyFragmentContainer, addProject);
        ft.addToBackStack(null);
        ft.commit();
    }
    // project adding successful
    @Override
    public void onProjectAddedSuccessful() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ProjectFaculty projectFaculty = new ProjectFaculty();
        ft.replace(R.id.facultyFragmentContainer, projectFaculty);
        ft.commit();
    }


    // THESIS SECTION
    // add thesis button click
    @Override
    public void onAddThesisSuccessful() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        AddThesis addThesis = new AddThesis();
        ft.replace(R.id.facultyFragmentContainer, addThesis);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void thesisPaperAddSuccessful() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ThesisFragment thesisFragment = new ThesisFragment();
        ft.replace(R.id.facultyFragmentContainer, thesisFragment);
        ft.commit();
    }
}