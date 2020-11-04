package com.example.studentteachercollaborations.FacultyPanel;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.studentteachercollaborations.R;

public class FacultyDashBoard extends Fragment {
    private OnBooksClickListener booksClickListener;
    private OnProjectClickListener projectClickListener;
    private OnNoticeClickListener noticeClickListener;
    private OnThesisClickListener thesisClickListener;
    private OnHelplineClickListener helplineClickListener;
    private OnQuestionsClickListener questionsClickListener;

    public FacultyDashBoard() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        booksClickListener = (OnBooksClickListener) context;
        projectClickListener = (OnProjectClickListener) context;
        noticeClickListener = (OnNoticeClickListener) context;
        thesisClickListener = (OnThesisClickListener) context;
        helplineClickListener = (OnHelplineClickListener) context;
        questionsClickListener = (OnQuestionsClickListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().setTitle("DASHBOARD");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);
        CardView books = view.findViewById(R.id.booksCV);
        CardView thesis = view.findViewById(R.id.thesisCV);
        CardView projects = view.findViewById(R.id.projectCV);
        CardView questions = view.findViewById(R.id.questionsCV);
        CardView helpline = view.findViewById(R.id.helplineCV);
        CardView notice = view.findViewById(R.id.noticeCV);

        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booksClickListener.onBooksClickSuccessful();
            }
        });

        thesis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thesisClickListener.onThesisClickSuccessful();
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
                questionsClickListener.questionsClickSuccessful();
            }
        });

        helpline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helplineClickListener.onHelplineClickSuccessful();
            }
        });

        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noticeClickListener.noticeClickSuccessful();
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

    public interface OnNoticeClickListener{
        void noticeClickSuccessful();
    }

    public interface OnThesisClickListener{
        void onThesisClickSuccessful();
    }

    public interface OnHelplineClickListener{
        void onHelplineClickSuccessful();
    }

    public interface OnQuestionsClickListener{
        void questionsClickSuccessful();
    }
}
