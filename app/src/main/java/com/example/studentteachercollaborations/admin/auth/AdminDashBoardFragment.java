package com.example.studentteachercollaborations.admin.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.studentteachercollaborations.R;
import com.example.studentteachercollaborations.admin.facultyinfo.AdminFacultyFragment;
import com.example.studentteachercollaborations.admin.report.AdminReportFragment;
import com.example.studentteachercollaborations.admin.studentinfo.AdminStudentsFragment;

public class AdminDashBoardFragment extends Fragment {

    public AdminDashBoardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_dash_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTitle("ADMIN DASHBOARD");

        CardView students, faculties, reports;
        students = view.findViewById(R.id.admin_student);
        faculties = view.findViewById(R.id.admin_faculty);
        reports = view.findViewById(R.id.admin_report);

        students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction(v, new AdminStudentsFragment());
            }
        });

        faculties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction(v, new AdminFacultyFragment());
            }
        });

        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction(v, new AdminReportFragment());
            }
        });
    }

    private void fragmentTransaction(View v, Fragment fragment){
        AppCompatActivity activity = (AppCompatActivity) v.getContext();
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.admin_fragment_container, fragment)
                .addToBackStack(null).commit();
    }
}