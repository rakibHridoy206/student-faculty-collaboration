package com.example.studentteachercollaborations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.studentteachercollaborations.FacultyPanel.FacultyActivity;
import com.example.studentteachercollaborations.StudentsPortal.StudentsActivity;

public class MainActivity extends AppCompatActivity{
    private CardView adminTV, facultyTV, studentTV;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Home Page");
        adminTV = findViewById(R.id.admin);
        facultyTV=findViewById(R.id.faculty);
        studentTV=findViewById(R.id.student);

        adminTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAdminPanel();
            }
        });

        facultyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFacultyPanel();
            }
        });

        studentTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStudentPortal();
            }
        });
    }

    private void openStudentPortal() {
        Intent intent = new Intent(this, StudentsActivity.class);
        startActivity(intent);
    }

    private void openFacultyPanel() {
        Intent intent = new Intent(this, FacultyActivity.class);
        startActivity(intent);
    }

    private void openAdminPanel() {
        Intent intent = new Intent(this, AdminPanelActivity.class);
        startActivity(intent);
    }
}