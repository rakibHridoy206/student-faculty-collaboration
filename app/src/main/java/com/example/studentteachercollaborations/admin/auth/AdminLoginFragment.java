package com.example.studentteachercollaborations.admin.auth;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.studentteachercollaborations.R;
import com.example.studentteachercollaborations.utils.AdminActivityStore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLoginFragment extends Fragment {
    private EditText emailET,passET;
    private Context context;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private DatabaseReference studentDatabaseReference;
    private DatabaseReference facultyDatabaseReference;

    private AdminActivityStore adminActivityStore;

    public AdminLoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference("AdminInfo");
        facultyDatabaseReference = FirebaseDatabase.getInstance().getReference("FACULTY_INFO");
        studentDatabaseReference = FirebaseDatabase.getInstance().getReference("STUDENTS_INFO");
        firebaseAuth = FirebaseAuth.getInstance();

        adminActivityStore = new AdminActivityStore(context);

        emailET = view.findViewById(R.id.adminEmailInputET);
        passET = view.findViewById(R.id.adminPasswordInputET);

        TextView login = view.findViewById(R.id.adminLoginBtn);

        if (adminActivityStore.getLoginStatus()){
            
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String email = emailET.getText().toString();
                String password = passET.getText().toString();

                if (emailET != null && passET != null){
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            facultyDatabaseReference.orderByChild("facultyEmail").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()){
                                        Toast.makeText(context, "There is no account for this email in admin panel", Toast.LENGTH_SHORT).show();
                                    }else {
                                        studentDatabaseReference.orderByChild("studentEmail").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.exists()){
                                                    Toast.makeText(context, "There is no account for this email in admin panel", Toast.LENGTH_SHORT).show();
                                                }else {
                                                    if (firebaseAuth.getCurrentUser() != null){
                                                        Toast.makeText(context, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                                                        successfullyLoggedIn(v);
                                                    }
                                                }
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void successfullyLoggedIn(View v) {
        AppCompatActivity activity = (AppCompatActivity) v.getContext();
        Fragment fragment = new AdminDashBoardFragment();
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.admin_fragment_container, fragment)
                .commit();
    }
}