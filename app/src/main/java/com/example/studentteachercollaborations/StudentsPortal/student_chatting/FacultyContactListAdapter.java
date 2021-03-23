package com.example.studentteachercollaborations.StudentsPortal.student_chatting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.studentteachercollaborations.FacultyPanel.FacultyInfo;
import com.example.studentteachercollaborations.R;

import java.util.ArrayList;
import java.util.List;

public class FacultyContactListAdapter extends RecyclerView.Adapter<FacultyContactListAdapter.ViewHolder> {

    List<FacultyInfo> facultyInfoList;
    Context context;

    public FacultyContactListAdapter(List<FacultyInfo> list, Context context) {
        this.facultyInfoList = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_contact_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.nameTV.setText(facultyInfoList.get(position).getFacultyName());
        holder.aboutTV.setText(facultyInfoList.get(position).getFacultyDesignation());

        Glide.with(context).load(facultyInfoList.get(position).getFacultyImageUrl())
                .placeholder(R.drawable.ic_perm_)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentFacultyMessageFragment.receiverID = facultyInfoList.get(position).getFacultyId();
                StudentFacultyMessageFragment.receiverName = facultyInfoList.get(position).getFacultyName();
                StudentFacultyMessageFragment.receiverImage = facultyInfoList.get(position).getFacultyImageUrl();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment messagingFragment = new StudentFacultyMessageFragment();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.studentFragmentContainerMain, messagingFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return facultyInfoList.size();
    }

    public void filterList(ArrayList<FacultyInfo> filteredList) {
        facultyInfoList = filteredList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTV,aboutTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.nameTV);
            aboutTV = itemView.findViewById(R.id.aboutTV);
            imageView = itemView.findViewById(R.id.profile_image);
        }
    }
}
