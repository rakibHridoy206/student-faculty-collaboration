package com.example.studentteachercollaborations.FacultyPanel.faculty_chatting;

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
import com.example.studentteachercollaborations.R;
import com.example.studentteachercollaborations.StudentsPortal.StudentInfo;

import java.util.ArrayList;
import java.util.List;

public class StudentContactListAdapter extends
        RecyclerView.Adapter<StudentContactListAdapter.ViewHolder> {
    List<StudentInfo> studentInfoList;
    Context context;

    public StudentContactListAdapter(List<StudentInfo> studentInfoList, Context context) {
        this.studentInfoList = studentInfoList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_student_contact_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.nameTV.setText(studentInfoList.get(position).getStudentName());
        holder.aboutTV.setText(studentInfoList.get(position).getStudentIntake());
        holder.intakeNo.setText(studentInfoList.get(position).getStudentId());

        Glide.with(context)
                .load(studentInfoList.get(position).getImageUrl())
                .placeholder(R.drawable.ic_perm_)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FacultyStudentMessageFragment.receiverID = studentInfoList.get(position).getStudentUserId();
                FacultyStudentMessageFragment.receiverImage = studentInfoList.get(position).getImageUrl();
                FacultyStudentMessageFragment.receiverName = studentInfoList.get(position).getStudentName();

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment messagingFragment = new FacultyStudentMessageFragment();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.facultyFragmentContainer, messagingFragment)
                        .addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentInfoList.size();
    }

    public void filterList(ArrayList<StudentInfo> filteredList) {
        studentInfoList = filteredList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTV,aboutTV, intakeNo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.nameTV);
            aboutTV = itemView.findViewById(R.id.aboutTV);
            intakeNo = itemView.findViewById(R.id.intake_no);
            imageView = itemView.findViewById(R.id.profile_image);
        }
    }


}
