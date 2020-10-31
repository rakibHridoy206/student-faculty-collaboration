package com.example.studentteachercollaborations;

import android.content.Context;
import android.os.Build;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {
    private Context context;
    private DatabaseReference databaseReferenceProjects;
    private DatabaseReference userRef;
    private List<ProjectInfo> projectInfoList;

    public ProjectAdapter(Context context, List<ProjectInfo> projectInfoList) {
        this.context = context;
        this.projectInfoList = projectInfoList;
        databaseReferenceProjects = FirebaseDatabase.getInstance().getReference("ProjectInformation");
        userRef = FirebaseDatabase.getInstance().getReference("FACULTY_INFO");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_projects_cardview, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,final int position) {
        final ProjectInfo info = projectInfoList.get(position);
        holder.projectNameTV.setText(projectInfoList.get(position).getProjectName());
        final String us = projectInfoList.get(position).getUserID();
        userRef.child(us).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object name = snapshot.child("facultyName").getValue();
                Object designation = snapshot.child("facultyDesignation").getValue();
                holder.projectPosterName.setText(String.valueOf(name));
                holder.projectPosterDes.setText(String.valueOf(designation));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popupView = LayoutInflater.from(context).inflate(R.layout.layout_project_details, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                final TextView projectName, projectDes, projectLink, userName, userDes;
                FloatingActionButton floatingActionButton;

                projectName = popupView.findViewById(R.id.projectShowNameTV);
                projectDes = popupView.findViewById(R.id.projectDescriptionShowTV);
                projectLink = popupView.findViewById(R.id.projectLinkShowTV);
                userName = popupView.findViewById(R.id.projectUserShowNameTV);
                userDes = popupView.findViewById(R.id.projectUserShowDescriptionTV);

                floatingActionButton = popupView.findViewById(R.id.close_details_fab);

                projectName.setText(projectInfoList.get(position).getProjectName());
                projectDes.setText(projectInfoList.get(position).getProjectDescription());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    projectDes.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
                }
                projectLink.setText(projectInfoList.get(position).getProjectLink());
                userRef.child(us).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Object name = snapshot.child("facultyName").getValue();
                        Object designation = snapshot.child("facultyDesignation").getValue();
                        userName.setText(String.valueOf(name));
                        userDes.setText(String.valueOf(designation));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                popupWindow.showAsDropDown(popupView, 0, 0);

                floatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                projectLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return projectInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView projectNameTV, projectPosterName, projectPosterDes;
        private CardView cardView;
        private PopupWindow popupWindow;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            projectNameTV = itemView.findViewById(R.id.project_NameTV);
            projectPosterName = itemView.findViewById(R.id.project_poster_name);
            projectPosterDes = itemView.findViewById(R.id.project_poster_des);
            cardView = itemView.findViewById(R.id.proectCardViewInfo);
        }
    }
}
