package com.example.studentteachercollaborations.admin.studentinfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.studentteachercollaborations.R;
import com.example.studentteachercollaborations.StudentsPortal.StudentInfo;
import com.example.studentteachercollaborations.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AdminStudentsFragment extends Fragment {
    private ExpandableListView expandableListView;
    private StudentExpandViewAdapter adapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHashMap;
    private final String[] departments = {
            "Computer Science & Engineering",
            "Electrical & Electronic Engineering",
            "Textile Engineering",
            "Civil Engineering",
            "Mathematics & Statistics",

            "Management",  "Accounting", "Marketing", "Finance",

            "English", "Economics", "Law & Justice"
    };
    private FirebaseAuth firebaseAuth;
    private DatabaseReference studentDatabaseReference;

    public AdminStudentsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_students, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        expandableListView = view.findViewById(R.id.expandableListView);
        studentDatabaseReference = FirebaseDatabase.getInstance().getReference("STUDENTS_INFO");
        firebaseAuth = FirebaseAuth.getInstance();
        initializeData();
        adapter = new StudentExpandViewAdapter(listDataHeader, listHashMap);
        expandableListView.setAdapter(adapter);

        onClickListeners();
    }

    private void onClickListeners() {
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String group = (String) adapter.getGroup(groupPosition);
                String child = (String) adapter.getChild(groupPosition, childPosition);

                Intent intent = new Intent(requireActivity(), StudentListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.DEPARTMENT_KEY, group);
                bundle.putString(Constants.INTAKE_KEY, child);
                intent.putExtra(Constants.BUNDLE_KEY, bundle);
                startActivity(intent);
                return true;
            }
        });
    }

    private void initializeData() {
        listDataHeader = new ArrayList<>();
        listHashMap = new HashMap<>();

        listDataHeader.addAll(Arrays.asList(departments));

        studentDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<StudentInfo> studentInfo = new ArrayList<>();
                for (DataSnapshot d: snapshot.getChildren()){
                    StudentInfo info = d.getValue(StudentInfo.class);
                    studentInfo.add(info);
                }

                for (String str: departments){
                    Set<String> intakes = new HashSet<>();
                    for (StudentInfo info: studentInfo){
                        if (str.equals(info.getStudentDepartment())){
                            intakes.add(info.getStudentIntake());
                        }
                    }

                    List<String> intakeList = new ArrayList<>(intakes);
                    listHashMap.put(str, intakeList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*for (String str: departments){
            final int finalI = i;
            studentDatabaseReference.orderByChild("studentDepartment").equalTo(str).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    List<String> intakes = new ArrayList<>();
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        StudentInfo studentInfo = dataSnapshot.getValue(StudentInfo.class);
                        String intake = studentInfo.getStudentIntake();
                        intakes.add(intake);
                    }
                    listHashMap.put(listDataHeader.get(finalI), intakes);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            i++;
        }*/
    }
}