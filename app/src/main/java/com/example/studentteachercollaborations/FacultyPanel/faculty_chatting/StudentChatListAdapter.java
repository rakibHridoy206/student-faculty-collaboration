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
import com.example.studentteachercollaborations.StudentsPortal.student_chatting.SenderReciverPojo;
import com.example.studentteachercollaborations.utils.HelperUtils;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class StudentChatListAdapter extends RecyclerView.Adapter<StudentChatListAdapter.ViewHolder> {
    List<SenderReciverPojo> list;
    Context context;

    public StudentChatListAdapter(List<SenderReciverPojo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.faculty_student_contract_row, parent, false);
        return new StudentChatListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String currentdate = HelperUtils.getDateWithTime();
        long different = HelperUtils.getDefferentBetweenTwoDate(currentdate, list.get(position).getStatus());
        if (different == 0) {
            String[] time = (list.get(position).getStatus()).split("\\s+");
            holder.dateTV.setText("Today " + time[2] + time[3]);
        } else if (different == 1) {
            String[] time = (list.get(position).getStatus()).split("\\s+");
            holder.dateTV.setText("Yesterday " + time[2] + time[3]);
        } else {
            holder.dateTV.setText(list.get(position).getStatus());
        }

        holder.msgTV.setText(list.get(position).getMsg());

        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            if ((FirebaseAuth.getInstance().getCurrentUser().getUid()).equals(list.get(position).getSenderID())) {
                holder.nameTV.setText(list.get(position).getReciverName());
                //  Picasso.get().load(list.get(position).getReciverImage()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.imageView);
                Glide.with(context)
                        .load(list.get(position).getReciverImage())
                        .placeholder(R.drawable.ic_image_black_24dp)
                        .into(holder.imageView);
            } else if ((FirebaseAuth.getInstance().getCurrentUser().getUid()).equals(list.get(position).getReciverID())) {
                holder.nameTV.setText(list.get(position).getSenderName());
                Glide.with(context)
                        .load(list.get(position).getSenderImage())
                        .placeholder(R.drawable.ic_image_black_24dp)
                        .into(holder.imageView);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((FirebaseAuth.getInstance().getCurrentUser().getUid()).equals(list.get(position).getReciverID())){
                    FacultyStudentMessageFragment.receiverID = list.get(position).getSenderID();
                    FacultyStudentMessageFragment.receiverImage = list.get(position).getSenderImage();
                    FacultyStudentMessageFragment.receiverName = list.get(position).getSenderName();
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    Fragment fragment = new FacultyStudentMessageFragment();
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.facultyFragmentContainer, fragment).addToBackStack(null).commit();

                }else if (((FirebaseAuth.getInstance().getCurrentUser().getUid()).equals(list.get(position).getSenderID()))) {
                    FacultyStudentMessageFragment.receiverID = list.get(position).getReciverID();
                    FacultyStudentMessageFragment.receiverImage = list.get(position).getReciverImage();
                    FacultyStudentMessageFragment.receiverName = list.get(position).getReciverName();
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    Fragment fragment = new FacultyStudentMessageFragment();
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.facultyFragmentContainer, fragment).addToBackStack(null).commit();
                    FacultyStudentMessageFragment.position = -1;
                }
                }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView,activeImageView;
        TextView nameTV, msgTV, dateTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.nameTV);
            msgTV = itemView.findViewById(R.id.msgTV);
            dateTV = itemView.findViewById(R.id.dateTV);
            imageView = itemView.findViewById(R.id.profile_image);
            activeImageView = itemView.findViewById(R.id.activeImageView);
        }
    }
}
