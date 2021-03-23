package com.example.studentteachercollaborations.StudentsPortal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

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
                        studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("First");
                        break;
                    case "Second Semester":
                        studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("Second");
                        break;
                    case "Third Semester":
                        studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("Third");
                        break;
                    case "Fourth Semester":
                        studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("Fourth");
                        break;
                    case "Fifth Semester":
                        studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("Fifth");
                        break;
                    case "Sixth Semester":
                        studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("Sixth");
                        break;
                    case "Seventh Semester":
                        studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("Seventh");
                        break;
                    case "Eighth Semester":
                        studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("Eighth");
                        break;
                    case "Ninth Semester":
                        studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("Ninth");
                        break;
                    case "Tenth Semester":
                        studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("Tenth");
                        break;
                    case "Eleventh Semester":
                        studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("Eleventh");
                        break;
                    case "Twelfth Semester":
                        studentsSemesterNoClick.onStudentsSemesterNoClickSuccessful("Twelfth");
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
