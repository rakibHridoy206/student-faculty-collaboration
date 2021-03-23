package com.example.studentteachercollaborations.admin.facultyinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentteachercollaborations.FacultyPanel.FacultyInfo;
import com.example.studentteachercollaborations.R;
import com.example.studentteachercollaborations.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FacultyListFragment extends Fragment {
    private String departmentName;
    private DatabaseReference facultyReference;
    private TextView department;
    private RecyclerView recyclerView;
    private List<FacultyInfo> facultyInfoList;
    private FacultyListAdapter adapter;

    public FacultyListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_faculty_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        departmentName = getArguments().getString(Constants.DEPARTMENT_KEY);
        facultyReference = FirebaseDatabase.getInstance().getReference(Constants.FACULTY_TABLE_NAME);
        department = view.findViewById(R.id.department_name);
        recyclerView = view.findViewById(R.id.faculty_list_recycler_view);
        facultyInfoList = new ArrayList<>();
        setUpUI();
    }

    private void setUpDataSet() {
        facultyReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //List<FacultyInfo> facultyInfoList = new ArrayList<>();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    FacultyInfo facultyInfo = dataSnapshot.getValue(FacultyInfo.class);
                    if (facultyInfo != null && facultyInfo.getFacultyDepartment().equals(departmentName)){
                        facultyInfoList.add(facultyInfo);
                    }
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setUpUI() {
        department.setText(departmentName);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new FacultyListAdapter(facultyInfoList, getContext());
        setUpDataSet();
        recyclerView.setAdapter(adapter);
    }
}