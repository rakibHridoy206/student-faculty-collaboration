package com.example.studentteachercollaborations;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.studentteachercollaborations.FacultyPanel.FacultyAuth.FacultyAuthActivity;
import com.example.studentteachercollaborations.StudentsPortal.StudentAuth.StudentAuthActivity;
import com.example.studentteachercollaborations.admin.auth.AdminPanelActivity;

public class MainActivity extends AppCompatActivity{
    Toolbar toolbar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.customToolbar);
        textView = findViewById(R.id.customBarTextView);
        setSupportActionBar(toolbar);

        CardView adminTV, facultyTV, studentTV;
        textView.setText(R.string.homePage);

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
        Intent intent = new Intent(this, StudentAuthActivity.class);
        startActivity(intent);
    }

    private void openFacultyPanel() {
        Intent intent = new Intent(this, FacultyAuthActivity.class);
        startActivity(intent);
    }

    private void openAdminPanel() {
        Intent intent = new Intent(this, AdminPanelActivity.class);
        startActivity(intent);
    }
}