package com.example.studentteachercollaborations.StudentsPortal.StudentAuth;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.studentteachercollaborations.R;
import com.example.studentteachercollaborations.StudentsPortal.StudentInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class StudentSignUp extends Fragment {
    private final String[] departments = {
            "Choose One From Below",
            "Computer Science & Engineering",
            "Electrical & Electronic Engineering",
            "Textile Engineering",
            "Civil Engineering",
            "Mathematics & Statistics",
            "Management",  "Accounting", "Marketing", "Finance",
            "English", "Economics", "Law & Justice"};
    private String department;
    private EditText nameET, emailET, phoneET, departmentET, intakeET, idET, passwordET, confirmPasswordET;
    private Spinner spinner;
    private Context context;
    private int mSelectedIndex = 0;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private OnAddStudentSuccessListener onAddStudentSuccessListener;
    private StorageReference storageReference;
    private Uri imageUri;
    private ImageView profileShow;

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
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("STUDENTS_INFO");
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference().child("StudentsProfilePictures");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_sign_up, container, false);
        TextView customToolbar, registerBtn;
        customToolbar = view.findViewById(R.id.customBarTextViewStudentSignUp);
        customToolbar.setText(R.string.studentSignUp);

        nameET = view.findViewById(R.id.studentNameInput);
        emailET = view.findViewById(R.id.studentEmailInput);
        phoneET = view.findViewById(R.id.studentPhoneNoInput);
        intakeET = view.findViewById(R.id.studentIntakeInput);
        idET = view.findViewById(R.id.studentIDInput);
        passwordET = view.findViewById(R.id.studentPasswordInput);
        confirmPasswordET = view.findViewById(R.id.studentConfirmPasswordInput);
        profileShow = view.findViewById(R.id.profileShowStudents);
        registerBtn = view.findViewById(R.id.studentRegisterBTN);
        final CardView profileImageView = view.findViewById(R.id.studentImageUpload);
        spinner = view.findViewById(R.id.studentDepartmentInput);

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectProfileImage();
            }
        });

        setUpSpinner();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = nameET.getText().toString();
                final String email = emailET.getText().toString();
                final String phone = phoneET.getText().toString();
                final String dept = department;
                final String intake = intakeET.getText().toString();
                final String id = idET.getText().toString();
                final String password = passwordET.getText().toString();
                final String confirmPassword = confirmPasswordET.getText().toString();


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
                }else if (dept.isEmpty()){
                    spinner.requestFocus();
                }else if (dept.equals("Choose One From Below")){
                    spinner.requestFocus();
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
                }else if (imageUri == null){
                    profileImageView.requestFocus();
                    Toast.makeText(getActivity(), "You did't select an image. Please select one",Toast.LENGTH_SHORT).show();
                }
                else{
                    final StorageReference imageName = storageReference.child(name+" "+ imageUri.getLastPathSegment());
                    final ProgressDialog pd = new ProgressDialog(context);
                    pd.setTitle("Profile image uploading...");
                    pd.show();

                    imageName.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    final String imageUrl = uri.toString();
                                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()){
                                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                                if (user != null){
                                                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                    StudentInfo studentInfo = new StudentInfo(userId, name, email, phone, dept, intake, id, password, imageUrl);
                                                    databaseReference.child(userId).setValue(studentInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()){
                                                                if (firebaseAuth.getCurrentUser() != null){
                                                                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if(task.isSuccessful()){
                                                                                snackbarShow("Registration Successful. Please check your email for verification");
                                                                                onAddStudentSuccessListener.onAddStudentSuccessful();
                                                                            }else {
                                                                                if (task.getException() != null){
                                                                                    Toast.makeText(context,task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                                                }
                                                                            }
                                                                        }
                                                                    });
                                                                }
                                                            }else {
                                                                if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                                                    snackbarShow("User is already registered");
                                                                }else {
                                                                    snackbarShow("Unsuccessful registration");
                                                                }
                                                            }
                                                        }
                                                    });
                                                }

                                            }
                                        }
                                    });
                                }
                            });
                            pd.dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            String ex = e.getLocalizedMessage();
                            Toast.makeText(context, ex , Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                            pd.setMessage("Image Uploaded: "+(int)progress+"%");
                        }
                    });


                }
            }
        });

        return view;
    }

    private void setUpSpinner() {
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this.requireActivity(), android.R.layout.simple_spinner_dropdown_item, departments){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView tv = (TextView) super.getView(position, convertView, parent);
                tv.setTextColor(Color.WHITE);
                return tv;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView tv = (TextView) super.getDropDownView(position, convertView, parent);
                tv.setTextColor(Color.WHITE);
                if (position == mSelectedIndex){
                    tv.setTextColor(Color.WHITE);
                }
                return tv;
            }
        };

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                department = parent.getSelectedItem().toString();
                mSelectedIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapter.setDropDownViewResource(R.layout.spinner_item_layout);
        spinner.setAdapter(adapter);
    }

    private void snackbarShow(String s){
        Snackbar make = Snackbar.make(requireActivity().findViewById(android.R.id.content), s, Snackbar.LENGTH_LONG);
        View snackbarLayout = make.getView();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT

        );
        lp.setMargins(200, 700, 200, 0);
        snackbarLayout.setLayoutParams(lp);
        snackbarLayout.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.colorLightBlue));
        make.show();
    }

    private void selectProfileImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data !=null && data.getData() != null){
            profileShow.setBackground(null);
            imageUri = data.getData();
            Bitmap bm =  null;
            if (imageUri != null) {
                try {
                    bm =    MediaStore.Images.Media.getBitmap(requireActivity().getApplicationContext().getContentResolver(), imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            profileShow.setImageBitmap(bm);
            profileShow.setRotation(90);
        }
    }

    public interface OnAddStudentSuccessListener{
        void onAddStudentSuccessful();
    }
}