package com.example.studentteachercollaborations.FacultyPanel.faculty_chatting;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentteachercollaborations.R;
import com.example.studentteachercollaborations.StudentsPortal.StudentInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentContactListFragment extends Fragment {
    FloatingActionButton searchActionButton;
    RecyclerView contractRV ;
    EditText searchET;
    StudentContactListAdapter studentContactListAdapter;

    List<StudentInfo> studentInfoList = new ArrayList<>();

    public StudentContactListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_contact_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("STUDENTS_INFO");

        contractRV = view.findViewById(R.id.std_contractRV);
        searchET = view.findViewById(R.id.std_searchET);
        searchActionButton = view.findViewById(R.id.std_searchActionButton);

        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        searchActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchET.animate().alpha(1.0f).setDuration(2000);;
                searchET.setVisibility(View.VISIBLE);
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                studentInfoList.clear();

                for (DataSnapshot data: snapshot.getChildren()){
                    studentInfoList.add(data.getValue(StudentInfo.class));
                }
                studentContactListAdapter = new StudentContactListAdapter(studentInfoList, getActivity());
                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                contractRV.setLayoutManager(llm);
                contractRV.setAdapter(studentContactListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void filter(String text) {
        ArrayList<StudentInfo> filteredList = new ArrayList<>();
        for (StudentInfo contractPojo : studentInfoList) {
            if ((contractPojo.getStudentName().toLowerCase()).contains(text.toLowerCase())) {
                filteredList.add(contractPojo);
            }
        }
        studentContactListAdapter.filterList(filteredList);
    }
}