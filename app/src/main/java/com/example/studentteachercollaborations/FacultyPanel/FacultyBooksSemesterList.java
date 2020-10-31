package com.example.studentteachercollaborations.FacultyPanel;

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

public class FacultyBooksSemesterList extends Fragment implements View.OnClickListener {
    private Context context;
    private OnFacultySemesterClick facultySemesterClick;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        facultySemesterClick = (OnFacultySemesterClick) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
                facultySemesterClick.onFacultySemesterClickSuccessful("FIRST");
                break;

            case R.id.secondSemester:
                facultySemesterClick.onFacultySemesterClickSuccessful("SECOND");
                break;

            case R.id.thirdSemester:
                facultySemesterClick.onFacultySemesterClickSuccessful("THIRD");
                break;

            case R.id.fourthSemester:
                facultySemesterClick.onFacultySemesterClickSuccessful("FOURTH");
                break;

            case R.id.fifthSemester:
                facultySemesterClick.onFacultySemesterClickSuccessful("FIFTH");
                break;

            case R.id.sixthSemester:
                facultySemesterClick.onFacultySemesterClickSuccessful("SIXTH");
                break;

            case R.id.seventhSemester:
                facultySemesterClick.onFacultySemesterClickSuccessful("SEVENTH");
                break;

            case R.id.eighthSemester:
                facultySemesterClick.onFacultySemesterClickSuccessful("EIGHTH");
                break;

            case R.id.ninthSemeter:
                facultySemesterClick.onFacultySemesterClickSuccessful("NINTH");
                break;

            case R.id.tenthSemester:
                facultySemesterClick.onFacultySemesterClickSuccessful("TENTH");
                break;

            case R.id.eleventhSemester:
                facultySemesterClick.onFacultySemesterClickSuccessful("ELEVENTH");
                break;

            case R.id.twelfthSemester:
                facultySemesterClick.onFacultySemesterClickSuccessful("TWELFTH");
                break;
        }

    }

    public interface OnFacultySemesterClick{
        void onFacultySemesterClickSuccessful(String semesterNo);
    }
}
