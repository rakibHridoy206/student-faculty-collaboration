package com.example.studentteachercollaborations.admin.facultyinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentteachercollaborations.R;
import com.example.studentteachercollaborations.utils.Constants;

import java.util.List;

public class FacultyDepartmentAdapter extends RecyclerView.Adapter<FacultyDepartmentAdapter.DepartmentViewHolder> {
    private final List<String> departmentList;

    public FacultyDepartmentAdapter(List<String> departmentList) {
        this.departmentList = departmentList;
    }

    @NonNull
    @Override
    public DepartmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DepartmentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_department_names, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DepartmentViewHolder holder, final int position) {
        holder.textView.setText(departmentList.get(position));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment fragment = new FacultyListFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.DEPARTMENT_KEY, departmentList.get(position));
                fragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().
                        replace(R.id.admin_fragment_container, fragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return departmentList.size();
    }

    public static class DepartmentViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView textView;
        public DepartmentViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.department_name_cv);
            textView = itemView.findViewById(R.id.department_name_tv);
        }
    }
}
