package com.example.studentteachercollaborations.StudentsPortal;

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

public class StudentDashBoard extends Fragment {
    private OnStudentsBooksClickListener booksClickListener;
    private OnStudentProjectClickListener projectClickListener;
    private OnStudentThesisClickListener thesisClickListener;
    private OnStudentQuestionsClickListener questionsClickListener;
    private OnStudentNoticeClickListener noticeClickListener;
    private OnStudentHelplineClickListener helplineClickListener;

    public StudentDashBoard() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        booksClickListener = (OnStudentsBooksClickListener) context;
        projectClickListener = (OnStudentProjectClickListener) context;
        thesisClickListener = (OnStudentThesisClickListener) context;
        questionsClickListener = (OnStudentQuestionsClickListener) context;
        noticeClickListener = (OnStudentNoticeClickListener) context;
        helplineClickListener = (OnStudentHelplineClickListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);
        final CardView books, thesis, projects, questions, helpline, notice;
        books = view.findViewById(R.id.booksCV);
        thesis = view.findViewById(R.id.thesisCV);
        projects = view.findViewById(R.id.projectCV);
        questions = view.findViewById(R.id.questionsCV);
        helpline = view.findViewById(R.id.helplineCV);
        notice = view.findViewById(R.id.noticeCV);

        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booksClickListener.onStudentsBooksClickSuccessful();
            }
        });

        projects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projectClickListener.onStudentProjectClickSuccessful();

            }
        });

        thesis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thesisClickListener.studentThesisClickSuccessful();
            }
        });

        questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionsClickListener.studentQuestionsClickSuccessful();
            }
        });

        helpline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helplineClickListener.studentHelplineClickSuccessful();
            }
        });

        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noticeClickListener.studentNoticeClickSuccessful();
            }
        });
        return view;
    }

    public interface OnStudentsBooksClickListener{
        void onStudentsBooksClickSuccessful();
    }

    public interface OnStudentProjectClickListener{
        void onStudentProjectClickSuccessful();
    }

    public interface OnStudentThesisClickListener{
        void studentThesisClickSuccessful();
    }

    public interface OnStudentQuestionsClickListener{
        void studentQuestionsClickSuccessful();
    }

    public interface OnStudentNoticeClickListener{
        void studentNoticeClickSuccessful();
    }

    public interface OnStudentHelplineClickListener{
        void studentHelplineClickSuccessful();
    }
}
