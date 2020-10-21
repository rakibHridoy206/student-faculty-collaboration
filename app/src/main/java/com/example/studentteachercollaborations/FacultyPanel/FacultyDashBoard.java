package com.example.studentteachercollaborations.FacultyPanel;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.studentteachercollaborations.R;

public class FacultyDashBoard extends Fragment {
    private Context context;
    private CardView books, thesis, projects, questions, helpline;
    private OnBooksClickListener booksClickListener;
    private OnProjectClickListener projectClickListener;

    public FacultyDashBoard() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        booksClickListener = (OnBooksClickListener) context;
        projectClickListener = (OnProjectClickListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("DASHBOARD");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);
        books = view.findViewById(R.id.booksCV);
        thesis = view.findViewById(R.id.thesisCV);
        projects = view.findViewById(R.id.projectCV);
        questions = view.findViewById(R.id.questionsCV);
        helpline = view.findViewById(R.id.helpCV);

        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booksClickListener.onBooksClickSuccessful();
            }
        });

        thesis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        projects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projectClickListener.onProjectClickSuccessful();
            }
        });

        questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        helpline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return view;
    }

    public interface OnBooksClickListener{
        void onBooksClickSuccessful();
    }

    public interface OnProjectClickListener{
        void onProjectClickSuccessful();
    }
}
