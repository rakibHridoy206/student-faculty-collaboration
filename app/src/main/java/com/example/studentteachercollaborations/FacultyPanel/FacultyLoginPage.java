package com.example.studentteachercollaborations.FacultyPanel;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.studentteachercollaborations.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FacultyLoginPage extends Fragment {
    private EditText emailET,passET;
    private Button loginBtn;
    private TextView signUpBtn;
    private Context context;
    private FirebaseAuth firebaseAuth;
    private FacultyAuthListener facultyAuthListener;
    private FacultySignUpListener facultySignUpListener;
    private DatabaseReference databaseReference;

    public FacultyLoginPage() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        facultyAuthListener = (FacultyAuthListener) context;
        facultySignUpListener = (FacultySignUpListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("LOGIN");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference("FACULTY_INFO");
        firebaseAuth = FirebaseAuth.getInstance();
        emailET = view.findViewById(R.id.emailInputET);
        passET = view.findViewById(R.id.passwordInputET);
        loginBtn = view.findViewById(R.id.loginBtn);
        signUpBtn = view.findViewById(R.id.signUpBTN);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailET.getText().toString();
                String password = passET.getText().toString();

                if(email.isEmpty()){
                    emailET.setError("Enter Email Address");
                    emailET.requestFocus();
                }else if(password.isEmpty()){
                    passET.setError("Enter Your Password");
                    passET.requestFocus();
                }else{
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull final Task<AuthResult> task) {
                            databaseReference.orderByChild("facultyEmail").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()){
                                        if (task.isSuccessful()) {
                                            if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                                facultyAuthListener.onFacultyLoginSuccessful();
                                                Toast.makeText(getActivity(), "Successfully Logged In",Toast.LENGTH_SHORT).show();
                                                facultyAuthListener.onFacultyLoginSuccessful();
                                            }else {
                                                Toast.makeText(context, "Please verify your email address.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }else{
                                        Toast.makeText(context, "You don't have an account in this section", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(context,error.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facultySignUpListener.onFacultySignupClickSuccess();
            }
        });
        return view;
    }

    public interface FacultyAuthListener{
        void onFacultyLoginSuccessful();
    }

    public interface FacultySignUpListener{
        void onFacultySignupClickSuccess();
    }
}
