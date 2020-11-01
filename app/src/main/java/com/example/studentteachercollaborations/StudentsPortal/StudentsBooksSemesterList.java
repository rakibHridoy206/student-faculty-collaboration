package com.example.studentteachercollaborations.StudentsPortal;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.studentteachercollaborations.R;

public class StudentsBooksSemesterList extends Fragment implements View.OnClickListener {
    private OnStudentsSemesterNoClick studentsSemesterNoClick;

    public StudentsBooksSemesterList() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        studentsSemesterNoClick = (OnStudentsSemesterNoClick) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_semester_list, container, false);
        requireActivity().setTitle("SEMESTER LIST FOR BOOKS");
        TextView first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth;

        first = view.findViewById(R.id.firstSemester);
        first.setOnClickListener(this);
        second = view.findViewById(R.id.secondSemester);
        second.setOnClickListener(this);
        third = view.findViewById(R.id.thirdSemester);
        third.setOnClickListener(this);
        fourth = view.findViewById(R.id.fourthSemester);
        fourth.setOnClickListener(this);
        fifth = view.findViewById(R.id.fifthSemester);
        fifth.setOnClickListener(this);
        sixth = view.findViewById(R.id.sixthSemester);
        sixth.setOnClickListener(this);
        seventh = view.findViewById(R.id.seventhSemester);
        seventh.setOnClickListener(this);
        eighth = view.findViewById(R.id.eighthSemester);
        eighth.setOnClickListener(this);
        ninth = view.findViewById(R.id.ninthSemeter);
        ninth.setOnClickListener(this);
        tenth = view.findViewById(R.id.tenthSemester);
        tenth.setOnClickListener(this);
        eleventh = view.findViewById(R.id.eleventhSemester);
        eleventh.setOnClickListener(this);
        twelfth = view.findViewById(R.id.twelfthSemester);
        twelfth.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.firstSemester:
                studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("FIRST");
                break;

            case R.id.secondSemester:
                studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("SECOND");
                break;

            case R.id.thirdSemester:
                studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("THIRD");
                break;

            case R.id.fourthSemester:
                studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("FOURTH");
                break;

            case R.id.fifthSemester:
                studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("FIFTH");
                break;

            case R.id.sixthSemester:
                studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("SIXTH");
                break;

            case R.id.seventhSemester:
                studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("SEVENTH");
                break;

            case R.id.eighthSemester:
                studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("EIGHTH");
                break;

            case R.id.ninthSemeter:
                studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("NINTH");
                break;

            case R.id.tenthSemester:
                studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("TENTH");
                break;

            case R.id.eleventhSemester:
                studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("ELEVENTH");
                break;

            case R.id.twelfthSemester:
                studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("TWELFTH");
                break;
        }
    }

    public interface OnStudentsSemesterNoClick{
        void onStudentsSemesterNoClickSuccessful(String semesterNo);
    }
}
