package com.example.studentteachercollaborations;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.studentteachercollaborations.FacultyPanel.FacultyActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Project extends Fragment{
    private Context context;
    private TextInputEditText projectNameET, projectDescriptionET, projectLinkET;
    private CardView saveTV;
    private DatabaseReference databaseReferenceProjects;
    private String currentUser;
    private OnProjectAdded projectAdded;

    public Project() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        projectAdded = (OnProjectAdded) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout, container, false);
        databaseReferenceProjects = FirebaseDatabase.getInstance().getReference("ProjectInformation");

        Bundle bundle = this.getArguments();
        if(bundle != null){
            currentUser = bundle.getString("key");
        }


        projectNameET = view.findViewById(R.id.project_nameET);
        projectDescriptionET = view.findViewById(R.id.project_descriptionET);
        projectLinkET = view.findViewById(R.id.project_linkET);
        saveTV = view.findViewById(R.id.project_save_button);

        saveTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String projectName = projectNameET.getText().toString();
                String projectDescription = projectDescriptionET.getText().toString();
                String projectLink = projectLinkET.getText().toString();
                String projectID = databaseReferenceProjects.push().getKey();

                ProjectInfo projectInfo = new ProjectInfo(projectID, projectName, projectDescription, projectLink, currentUser);

                if (projectID != null){
                    databaseReferenceProjects.child(projectID).setValue(projectInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(context, "Your data have been collected, thank you!", Toast.LENGTH_SHORT).show();
                            projectAdded.onProjectAddedSuccessful();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Recheck again, thank you!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(context, "Database has not created", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public interface OnProjectAdded{
        void onProjectAddedSuccessful();
    }
}
