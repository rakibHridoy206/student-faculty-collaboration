package com.example.studentteachercollaborations.FacultyPanel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentteachercollaborations.ProjectAdapter;
import com.example.studentteachercollaborations.ProjectInfo;
import com.example.studentteachercollaborations.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProjectFaculty extends Fragment {
    private Context context;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReferenceProjects;
    private ProjectAdapter projectAdapter;
    private OnAddProjectClick addProjectClick;

    public ProjectFaculty() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        addProjectClick = (OnAddProjectClick) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects, container, false);
        final String[] facultyName = new String[1];
        final String[] facultyDesignation = new String[1];

        databaseReferenceProjects = FirebaseDatabase.getInstance().getReference("ProjectInformation");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userID = currentUser.getUid();

        fab = view.findViewById(R.id.addProjectFab);
        recyclerView = view.findViewById(R.id.projectRV);

        databaseReferenceProjects.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<ProjectInfo> infoList = new ArrayList<>();
                for(DataSnapshot d: snapshot.getChildren()){
                    ProjectInfo projectInfo = d.getValue(ProjectInfo.class);
                    infoList.add(projectInfo);
                }
                projectAdapter = new ProjectAdapter(context, infoList);
                LinearLayoutManager llm = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(projectAdapter);
                projectAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userID.isEmpty()){
                    Toast.makeText(context, "System cannot find your id", Toast.LENGTH_SHORT).show();
                }else{
                    addProjectClick.onAddProjectClickSuccessful(userID);
                }
            }
        });
        return view;
    }

    public interface OnAddProjectClick{
        void onAddProjectClickSuccessful(String id);
    }
}
