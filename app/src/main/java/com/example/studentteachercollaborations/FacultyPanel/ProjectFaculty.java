package com.example.studentteachercollaborations.FacultyPanel;

import android.content.Context;
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

import com.example.studentteachercollaborations.CommonFeatures.Project.ProjectAdapter;
import com.example.studentteachercollaborations.CommonFeatures.Project.ProjectInfo;
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
    private RecyclerView recyclerView;
    private ProjectAdapter projectAdapter;
    private OnFacultyAddProjectClick addProjectClick;

    public ProjectFaculty() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        addProjectClick = (OnFacultyAddProjectClick) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().setTitle("PROJECTS");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects, container, false);

        DatabaseReference databaseReferenceProjects = FirebaseDatabase.getInstance().getReference("ProjectInformation");
        final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("FACULTY_INFO");
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


        FloatingActionButton fab = view.findViewById(R.id.addProjectFab);
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
                if(currentUser != null){
                    final String userID = currentUser.getUid();
                    if(userID.isEmpty()){
                        Toast.makeText(context, "System cannot find your id", Toast.LENGTH_SHORT).show();
                    }else{
                        userRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Object name = snapshot.child("facultyName").getValue();
                                Object designation = snapshot.child("facultyDesignation").getValue();
                                String n = String.valueOf(name);
                                String d = String.valueOf(designation);
                                addProjectClick.onAddProjectClickSuccessful(n, d);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }
            }
        });
        return view;
    }

    public interface OnFacultyAddProjectClick {
        void onAddProjectClickSuccessful(String name, String designation);
    }
}
