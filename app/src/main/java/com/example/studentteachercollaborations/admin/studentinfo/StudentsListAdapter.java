package com.example.studentteachercollaborations.admin.studentinfo;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.studentteachercollaborations.R;
import com.example.studentteachercollaborations.StudentsPortal.StudentInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentsListAdapter extends RecyclerView.Adapter<StudentsListAdapter.StudentsListViewHolder> {
    private final List<StudentInfo> studentInfoList;
    private final Context context;

    public StudentsListAdapter(List<StudentInfo> studentInfoList, Context context) {
        this.studentInfoList = studentInfoList;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_students_list, parent, false);
        return new StudentsListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentsListViewHolder holder, int position) {
        final StudentInfo studentInfo = studentInfoList.get(position);
        
        holder.name.setText(studentInfo.getStudentName());
        holder.id.setText(studentInfo.getStudentId());
        Glide.with(context).load(Uri.parse(studentInfo.getImageUrl())).into(holder.profileImageView);

        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                String delete = studentInfo.getStudentUserId();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                reference.child("STUDENTS_INFO").child(delete).removeValue();
                notifyDataSetChanged();
                Log.wtf("obito", "removed");
                Query query = reference.child("STUDENTS_INFO").orderByChild("studentUserId").equalTo(delete);

                /*query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot d: snapshot.getChildren()){
                            d.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentInfoList.size();
    }

    public static class StudentsListViewHolder extends RecyclerView.ViewHolder {
        TextView name, id;
        CircleImageView profileImageView;
        AppCompatImageView deleteImageView;
        public StudentsListViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.student_name);
            id = itemView.findViewById(R.id.student_id);
            profileImageView = itemView.findViewById(R.id.student_image);
            deleteImageView = itemView.findViewById(R.id.delete_student);
        }
    }
}
