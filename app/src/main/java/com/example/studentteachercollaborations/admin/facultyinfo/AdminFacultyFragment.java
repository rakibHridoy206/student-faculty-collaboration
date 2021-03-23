package com.example.studentteachercollaborations.admin.facultyinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentteachercollaborations.R;
import com.example.studentteachercollaborations.utils.Constants;

import java.util.Arrays;
import java.util.List;

public class AdminFacultyFragment extends Fragment {
    private RecyclerView recyclerView;

    public AdminFacultyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_faculty, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.faculty_department_rv);
        initializeUI();
    }

    private void initializeUI() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        List<String> departments = Arrays.asList(Constants.DEPARTMENT_NAMES);
        FacultyDepartmentAdapter adapter = new FacultyDepartmentAdapter(departments);
        recyclerView.setAdapter(adapter);
    }
}