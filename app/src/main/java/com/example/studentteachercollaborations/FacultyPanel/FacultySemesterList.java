package com.example.studentteachercollaborations.FacultyPanel;

import android.content.Context;
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
import com.example.studentteachercollaborations.SemesterList;

public class FacultySemesterList extends Fragment {
    private Context context;
    private OnFacultySemesterNoClick facultySemesterNoClick;

    public FacultySemesterList() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        facultySemesterNoClick = (OnFacultySemesterNoClick) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("ALL SEMESTERS");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faculty_semester_lists, container, false);
        SemesterList[] semesterLists = new SemesterList[]{
                new SemesterList("First Semester"),
                new SemesterList("Second Semester"),
                new SemesterList("Third Semester"),
                new SemesterList("Fourth Semester"),
                new SemesterList("Fifth Semester"),
                new SemesterList("Sixth Semester"),
                new SemesterList("Seventh Semester"),
                new SemesterList("Eighth Semester"),
                new SemesterList("Ninth Semester"),
                new SemesterList("Tenth Semester"),
                new SemesterList("Eleventh Semester"),
                new SemesterList("Twelfth Semester")
        };

        RecyclerView recyclerView = view.findViewById(R.id.booksRecyclerView);
        FacultySemesterListAdapter listAdapter = new FacultySemesterListAdapter(context, semesterLists, facultySemesterNoClick);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(listAdapter);
        return view;
    }

    public interface OnFacultySemesterNoClick{
        void onFacultySemesterNoClickSuccessful(String semesterNo);
    }
}
