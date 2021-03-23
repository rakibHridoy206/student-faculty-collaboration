package com.example.studentteachercollaborations.CommonFeatures.Thesis;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.example.studentteachercollaborations.CommonFeatures.Thesis.ThesisInfo;
import com.example.studentteachercollaborations.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;

public class AddThesis extends Fragment {
    private Context context;
    private DatabaseReference databaseReference;
    private TextInputEditText thesisNameET, thesisAuthorsET;
    private TextView thesisPdf;
    private Uri uriData;
    private StorageReference storageReference;
    private OnThesisPaperAddClick thesisPaperAddClick;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        thesisPaperAddClick = (OnThesisPaperAddClick) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_add_thesis, container, false);
        requireActivity().setTitle("ADDING RESEARCH PAPER");
        databaseReference = FirebaseDatabase.getInstance().getReference("ThesisInformation");
        storageReference = FirebaseStorage.getInstance().getReference().child("ThesisPaperPDFs");

        thesisAuthorsET = view.findViewById(R.id.thesis_authorsET);
        thesisNameET = view.findViewById(R.id.thesis_nameET);
        thesisPdf = view.findViewById(R.id.thesis_pdfTV);
        CardView saveBTN = view.findViewById(R.id.thesis_save_button);

        thesisPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectThesisPDF();
            }
        });

        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(thesisAuthorsET.getText() != null && thesisNameET.getText() != null && uriData != null){
                    final String title = thesisNameET.getText().toString();
                    final String authors = thesisAuthorsET.getText().toString();
                    final String thesisID = databaseReference.push().getKey();
                    final String fileName = thesisPdf.getText().toString();
                    final ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setTitle("UPLOADING....");
                    progressDialog.show();
                    final StorageReference researchFile = storageReference.child(title);
                    researchFile.putFile(uriData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            researchFile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    final String pdfUrl = uri.toString();
                                    ThesisInfo thesisInfo = new ThesisInfo(thesisID, title, authors, pdfUrl, fileName);
                                    if (thesisID != null){
                                        databaseReference.child(thesisID).setValue(thesisInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                thesisPaperAddClick.thesisPaperAddSuccessful();
                                                Toast.makeText(context, "Information uploaded", Toast.LENGTH_LONG).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Book Uploaded: "+(int)progress+"%");
                        }
                    });
                }

            }
        });

        return view;
    }

    private void selectThesisPDF() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF File"),1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data !=null && data.getData() != null){
            uriData = data.getData();
            thesisPdf.setText(data.getData().getLastPathSegment());
        }
    }

    public interface OnThesisPaperAddClick{
        void thesisPaperAddSuccessful();
    }
}
