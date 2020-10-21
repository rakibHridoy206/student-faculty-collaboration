package com.example.studentteachercollaborations.StudentsPortal;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentteachercollaborations.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentSignUp extends Fragment {
    private EditText nameET, emailET, phoneET, intakeET, idET, passwordET, confirmPasswordET;
    private Button registerBtn;
    private Context context;

    private DatabaseReference databaseReference;
    private DatabaseReference intakeReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    private OnAddStudentSuccessListener onAddStudentSuccessListener;

    public StudentSignUp() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        onAddStudentSuccessListener = (OnAddStudentSuccessListener) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("SIGN UP");
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("STUDENTS_INFO");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_sign_up, container, false);
        nameET = view.findViewById(R.id.studentNameInput);
        emailET = view.findViewById(R.id.studentEmailInput);
        phoneET = view.findViewById(R.id.studentPhoneNoInput);
        intakeET = view.findViewById(R.id.studentIntakeInput);
        idET = view.findViewById(R.id.studentIDInput);
        passwordET = view.findViewById(R.id.studentPasswordInput);
        confirmPasswordET = view.findViewById(R.id.studentConfirmPasswordInput);
        registerBtn = view.findViewById(R.id.studentRegisterBTN);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = nameET.getText().toString();
                final String email = emailET.getText().toString();
                final String phone = phoneET.getText().toString();
                final String intake = intakeET.getText().toString();
                final String id = idET.getText().toString();
                final String password = passwordET.getText().toString();
                final String confirmPassword = confirmPasswordET.getText().toString();
                currentUser = FirebaseAuth.getInstance().getCurrentUser();

                if(name.isEmpty()){
                    nameET.setError("Enter your name");
                    nameET.requestFocus();
                }else if(email.isEmpty()){
                    emailET.setError("Enter your email");
                    emailET.requestFocus();
                }else if(phone.isEmpty()){
                    phoneET.setError("Enter your mobile number");
                    phoneET.requestFocus();
                }else if(phone.length()!=11) {
                    phoneET.setError("Enter your valid mobile number");
                    phoneET.requestFocus();
                }else if(intake.isEmpty()){
                    intakeET.setError("Enter your intake number");
                    intakeET.requestFocus();
                }else if(id.isEmpty()){
                    idET.setError("Enter your full id correctly");
                    idET.requestFocus();
                }else if(id.length()!=11) {
                    idET.setError("Enter your valid mobile number");
                    idET.requestFocus();
                }else if(password.isEmpty()){
                    passwordET.setError("Enter a password");
                    passwordET.requestFocus();
                }else if(confirmPassword.isEmpty()){
                    confirmPasswordET.setError("Enter password again to confirm");
                    confirmPasswordET.requestFocus();
                }else if (!confirmPassword.equals(password)) {
                    confirmPasswordET.requestFocus();
                    Toast.makeText(getActivity(), "Password doesn't matched. Please re-enter password",Toast.LENGTH_SHORT).show();
                }else{
                    intakeReference = databaseReference.child(intake);
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                StudentInfo studentInfo = new StudentInfo(userId, name, email, phone, intake, id, password);
                                intakeReference.child(userId).setValue(studentInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(context,"Registration Successful. Please check your email for verification",Toast.LENGTH_LONG).show();
                                                        onAddStudentSuccessListener.onAddStudentSuccessful();
                                                    }else {
                                                        Toast.makeText(context,task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                        }else {
                                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                                Toast.makeText(context, "User is already registered", Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(context, "Unsuccessful registration", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

        return view;
    }

    public interface OnAddStudentSuccessListener{
        void onAddStudentSuccessful();
    }
}