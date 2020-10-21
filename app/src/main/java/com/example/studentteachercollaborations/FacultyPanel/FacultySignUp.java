package com.example.studentteachercollaborations.FacultyPanel;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.studentteachercollaborations.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FacultySignUp extends Fragment implements AdapterView.OnItemSelectedListener {
    private String[] designations = {"Choose One From Below","Professor", "Associate Professor", "Assistant Professor", "Lecturer"};
    private String designation;
    private EditText nameET, emailET, phoneET, passwordET, confirmPasswordET;
    private Button registerBtn;
    private Spinner spinner;
    private Context context;

    private DatabaseReference databaseReference;
    private DatabaseReference facultyReference;
    private FirebaseAuth firebaseAuth;

    private OnAddFacultySuccessListener addFacultySuccessListener;

    public FacultySignUp() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        addFacultySuccessListener = (OnAddFacultySuccessListener) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().setTitle("SIGN UP");
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("FACULTY_INFO");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faculty_sign_up, container, false);

        spinner = view.findViewById(R.id.facultySpinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter adapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, designations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        nameET = view.findViewById(R.id.facultyNameInput);
        emailET = view.findViewById(R.id.facultyEmailInput);
        phoneET = view.findViewById(R.id.facultyPhoneNoInput);
        passwordET = view.findViewById(R.id.facultyPasswordInput);
        confirmPasswordET = view.findViewById(R.id.facultyConfirmPasswordInput);
        registerBtn = view.findViewById(R.id.facultyRegisterBTN);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = nameET.getText().toString();
                final String email = emailET.getText().toString();
                final String phone = phoneET.getText().toString();
                final String password = passwordET.getText().toString();
                final String confirmPassword = confirmPasswordET.getText().toString();
                final String d = designation;

                if(name.isEmpty()){
                    nameET.setError("Enter your name");
                    nameET.requestFocus();
                }else if(email.isEmpty()){
                    emailET.setError("Enter your email");
                    emailET.requestFocus();
                }else if(phone.isEmpty()){
                    phoneET.setError("Enter your mobile number");
                    phoneET.requestFocus();
                }else if(phone.length()!=11){
                    phoneET.setError("Enter your valid mobile number");
                    phoneET.requestFocus();
                }else if(designation == "Choose One From Below"){
                    spinner.requestFocus();
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
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                FacultyInfo facultyInfo = new FacultyInfo(id, name, email, phone, d, password);
                                databaseReference.child(id).setValue(facultyInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(context,"Registration Successful. Please check your email for verification",Toast.LENGTH_LONG).show();
                                                        addFacultySuccessListener.onAddFacultySuccessfull();
                                                    }else {
                                                        Toast.makeText(context,task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                        }else{
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        designation = parent.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface OnAddFacultySuccessListener{
        void onAddFacultySuccessfull();
    }
}