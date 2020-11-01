package com.example.studentteachercollaborations.StudentsPortal;

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

public class ProjectStudents extends Fragment {
    private Context context;
    private RecyclerView recyclerView;
    private ProjectAdapter projectAdapter;
    private OnStudentAddProjectClick studentAddProjectClick;

    public ProjectStudents() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        studentAddProjectClick = (OnStudentAddProjectClick) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects, container, false);
        requireActivity().setTitle("PROJECTS");
        DatabaseReference databaseReferenceProjects = FirebaseDatabase.getInstance().getReference("ProjectInformation");
        final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("STUDENTS_INFO");
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
                                Object name = snapshot.child("studentName").getValue();
                                Object intake = snapshot.child("studentIntake").getValue();
                                Object id = snapshot.child("studentId").getValue();
                                String n = String.valueOf(name);
                                String in = String.valueOf(intake);
                                String i = String.valueOf(id);
                                String d = "Intake: "+in+", ID: "+i;
                                studentAddProjectClick.studentAddProjectClickSuccessful(n, d);
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

    public interface OnStudentAddProjectClick {
        void studentAddProjectClickSuccessful(String name, String designation);
    }
}
