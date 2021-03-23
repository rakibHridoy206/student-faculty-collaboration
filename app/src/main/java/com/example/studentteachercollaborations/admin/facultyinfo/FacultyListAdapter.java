package com.example.studentteachercollaborations.admin.facultyinfo;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.studentteachercollaborations.FacultyPanel.FacultyInfo;
import com.example.studentteachercollaborations.R;
import com.example.studentteachercollaborations.utils.Constants;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FacultyListAdapter extends RecyclerView.Adapter<FacultyListAdapter.FacultyListViewHolder> {
    private final List<FacultyInfo> facultyInfoList;
    private final Context context;

    public FacultyListAdapter(List<FacultyInfo> facultyInfoList, Context context) {
        this.facultyInfoList = facultyInfoList;
        this.context = context;
    }

    @NonNull
    @Override
    public FacultyListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FacultyListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_faculty_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FacultyListViewHolder holder, int position) {
        final FacultyInfo facultyInfo = facultyInfoList.get(position);

        holder.name.setText(facultyInfo.getFacultyName());
        holder.designation.setText(facultyInfo.getFacultyDesignation());
        Glide.with(context).load(Uri.parse(facultyInfo.getFacultyImageUrl())).into(holder.profileImageView);
        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String delete = facultyInfo.getFacultyId();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                reference.child(Constants.FACULTY_TABLE_NAME).child(delete).removeValue();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return facultyInfoList.size();
    }

    public static class FacultyListViewHolder extends RecyclerView.ViewHolder {
        TextView name, designation;
        CircleImageView profileImageView;
        AppCompatImageView deleteImageView;

        public FacultyListViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.faculty_name);
            designation = itemView.findViewById(R.id.faculty_designation);
            profileImageView = itemView.findViewById(R.id.faculty_image);
            deleteImageView = itemView.findViewById(R.id.delete_faculty);
        }
    }
}
