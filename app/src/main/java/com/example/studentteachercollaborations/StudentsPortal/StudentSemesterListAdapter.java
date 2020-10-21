package com.example.studentteachercollaborations.StudentsPortal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentteachercollaborations.FacultyPanel.FacultySemesterListAdapter;
import com.example.studentteachercollaborations.R;
import com.example.studentteachercollaborations.SemesterList;

public class StudentSemesterListAdapter extends RecyclerView.Adapter<StudentSemesterListAdapter.ViewHolder> {
    private Context context;
    private SemesterList[] semesterLists;
    private StudentsBooksSemesterList.OnStudentsSemesterNoClick studentsSemesterNoClick;

    public StudentSemesterListAdapter(Context context, SemesterList[] semesterLists,
                                      StudentsBooksSemesterList.OnStudentsSemesterNoClick studentsSemesterNoClick) {
        this.context = context;
        this.semesterLists = semesterLists;
        this.studentsSemesterNoClick = studentsSemesterNoClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.layout_list_books, parent, false);
        StudentSemesterListAdapter.ViewHolder viewHolder = new StudentSemesterListAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final SemesterList semesterListData = semesterLists[position];
        holder.semesterNoTV.setText(semesterLists[position].getSemesterNo());
        holder.booksCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String semesterNo = semesterLists[position].getSemesterNo();
                switch (semesterNo){
                    case "First Semester":
                        studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("Semester_1");
                        break;
                    case "Second Semester":
                        studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("Semester_2");
                        break;
                    case "Third Semester":
                        studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("Semester_3");
                        break;
                    case "Fourth Semester":
                        studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("Semester_4");
                        break;
                    case "Fifth Semester":
                        studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("Semester_5");
                        break;
                    case "Sixth Semester":
                        studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("Semester_6");
                        break;
                    case "Seventh Semester":
                        studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("Semester_7");
                        break;
                    case "Eighth Semester":
                        studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("Semester_8");
                        break;
                    case "Ninth Semester":
                        studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("Semester_9");
                        break;
                    case "Tenth Semester":
                        studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("Semester_10");
                        break;
                    case "Eleventh Semester":
                        studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("Semester_11");
                        break;
                    case "Twelfth Semester":
                        studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("Semester_12");
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return semesterLists.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView semesterNoTV;
        private CardView booksCardView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.semesterNoTV = itemView.findViewById(R.id.semesterNo);
            this.booksCardView = itemView.findViewById(R.id.booksCard);
        }
    }
}
