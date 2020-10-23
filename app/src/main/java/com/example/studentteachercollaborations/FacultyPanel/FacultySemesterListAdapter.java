package com.example.studentteachercollaborations.FacultyPanel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentteachercollaborations.R;
import com.example.studentteachercollaborations.SemesterList;

public class FacultySemesterListAdapter extends RecyclerView.Adapter<FacultySemesterListAdapter.ViewHolder> {
    private Context context;
    private SemesterList[] semesterLists;
    private FacultySemesterList.OnFacultySemesterNoClick facultySemesterNoClick;

    public FacultySemesterListAdapter(Context context, SemesterList[] semesterLists,
                                      FacultySemesterList.OnFacultySemesterNoClick facultySemesterNoClick) {
        this.context = context;
        this.semesterLists = semesterLists;
        this.facultySemesterNoClick = facultySemesterNoClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.layout_list_books, parent, false);
        return new ViewHolder(listItem);
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
                        facultySemesterNoClick.onFacultySemesterNoClickSuccessful("First");
                        break;
                    case "Second Semester":
                        facultySemesterNoClick.onFacultySemesterNoClickSuccessful("Second");
                        break;
                    case "Third Semester":
                        facultySemesterNoClick.onFacultySemesterNoClickSuccessful("Third");
                        break;
                    case "Fourth Semester":
                        facultySemesterNoClick.onFacultySemesterNoClickSuccessful("Fourth");
                        break;
                    case "Fifth Semester":
                        facultySemesterNoClick.onFacultySemesterNoClickSuccessful("Fifth");
                        break;
                    case "Sixth Semester":
                        facultySemesterNoClick.onFacultySemesterNoClickSuccessful("Sixth");
                        break;
                    case "Seventh Semester":
                        facultySemesterNoClick.onFacultySemesterNoClickSuccessful("Seventh");
                        break;
                    case "Eighth Semester":
                        facultySemesterNoClick.onFacultySemesterNoClickSuccessful("Eighth");
                        break;
                    case "Ninth Semester":
                        facultySemesterNoClick.onFacultySemesterNoClickSuccessful("Ninth");
                        break;
                    case "Tenth Semester":
                        facultySemesterNoClick.onFacultySemesterNoClickSuccessful("Tenth");
                        break;
                    case "Eleventh Semester":
                        facultySemesterNoClick.onFacultySemesterNoClickSuccessful("Eleventh");
                        break;
                    case "Twelfth Semester":
                        facultySemesterNoClick.onFacultySemesterNoClickSuccessful("Twelfth");
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
