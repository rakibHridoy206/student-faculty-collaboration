package com.example.studentteachercollaborations.admin.studentinfo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentteachercollaborations.R;
import com.example.studentteachercollaborations.StudentsPortal.StudentInfo;
import com.example.studentteachercollaborations.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentListActivity extends AppCompatActivity {
    private String group, child;
    private TextView department, intake;
    private RecyclerView recyclerView;
    private DatabaseReference studentReference;
    List<StudentInfo> departmentStudents;
    private StudentsListAdapter studentsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        studentReference = FirebaseDatabase.getInstance().getReference(Constants.STUDENT_TABLE_NAME);
        recyclerView = findViewById(R.id.student_list_recycler_view);
        department = findViewById(R.id.department_name);
        intake = findViewById(R.id.intake_number);
        departmentStudents = new ArrayList<>();

        Bundle bundle = getIntent().getBundleExtra(Constants.BUNDLE_KEY);
        if (bundle != null){
            group = bundle.getString(Constants.DEPARTMENT_KEY);
            child = bundle.getString(Constants.INTAKE_KEY);
        } else {
            Toast.makeText(this, Constants.DATA_NOT_FOUND, Toast.LENGTH_SHORT).show();
        }
        initializeUI();
    }

    @SuppressLint("SetTextI18n")
    private void initializeUI() {
        department.setText(group);
        intake.setText("Intake: "+child);

        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);
        initializeData();
        studentsListAdapter = new StudentsListAdapter(departmentStudents, this);
        recyclerView.setAdapter(studentsListAdapter);
    }

    private void initializeData() {
        studentReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<StudentInfo> studentInfoList = new ArrayList<>();
                for (DataSnapshot d: snapshot.getChildren()){
                    StudentInfo studentInfo = d.getValue(StudentInfo.class);
                    studentInfoList.add(studentInfo);
                }

                for (StudentInfo studentInfo: studentInfoList){
                    if (studentInfo.getStudentIntake().equals(child) && studentInfo.getStudentDepartment().equals(group)){
                        departmentStudents.add(studentInfo);
                    }
                }
                studentsListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}