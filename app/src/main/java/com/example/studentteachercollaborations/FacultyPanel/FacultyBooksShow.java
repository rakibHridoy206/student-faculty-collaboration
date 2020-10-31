package com.example.studentteachercollaborations.FacultyPanel;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.example.studentteachercollaborations.R;
import com.example.studentteachercollaborations.ReadPdfActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import static android.app.Activity.RESULT_OK;

public class FacultyBooksShow extends Fragment {
    private Context context;
    private ListView listView;
    private StorageReference storageReference;
    private DatabaseReference databaseReferenceSemesters;
    private List<UploadPdfFiles> uploadFilesList;
    private String fileName;
    private String storageData;
    private TextView uploadName;
    private Uri uriData;
    private ArrayAdapter<String> arrayAdapter;

    public FacultyBooksShow() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null){
            final String data = bundle.getString("key");
            storageData = data;
            requireActivity().setTitle(data+" SEMESTER BOOKS");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faculty_books, container, false);
        listView = view.findViewById(R.id.pdfListView);
        FloatingActionButton fab = view.findViewById(R.id.pdfFAV);

        storageReference = FirebaseStorage.getInstance().getReference().child(storageData);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UploadedBooksLinks");
        databaseReferenceSemesters = databaseReference.child(storageData);

        uploadFilesList = new ArrayList<>();
        showAllPDFs();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idM = Long.toString(id);
                Toast.makeText(context, idM, Toast.LENGTH_SHORT).show();
                final int pos = position;

                final PopupMenu popup = new PopupMenu(context, view);
                popup.inflate(R.menu.list_menu_faculty);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.readPdfFaculty:
                                readPdf(pos);
                                return true;

                            case R.id.deleteBookFaculty:
                                deletePdf(pos);
                                popup.dismiss();
                                return true;

                            default:
                                return false;
                        }
                    }
                });
                popup.show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.layout_add_books);
                final EditText bookName;
                final Button uploadBTN;
                uploadName = dialog.findViewById(R.id.uploadName);

                bookName = dialog.findViewById(R.id.fileNameET);
                uploadBTN = dialog.findViewById(R.id.uploadPDF);

                uploadName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fileName = bookName.getText().toString();
                        selectPdf();
                    }
                });

                uploadBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        uploadPDF(uriData);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        return view;
    }

    private void selectPdf() {
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
            uploadName.setText(data.getData().getLastPathSegment());
        }
    }

    private void uploadPDF(final Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("UPLOADING....");
        progressDialog.show();
        final String storageFileName = data.getLastPathSegment();
        if (storageFileName != null){
            final StorageReference pdfName = storageReference.child(storageFileName);
            pdfName.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    pdfName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final String pdfUrl = uri.toString();
                            String pdfID = databaseReferenceSemesters.push().getKey();
                            UploadPdfFiles uploadFiles = new UploadPdfFiles(pdfID, fileName, pdfUrl, storageFileName);
                            if (pdfID != null){
                                databaseReferenceSemesters.child(pdfID).setValue(uploadFiles).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "Book uploaded", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    });
                    progressDialog.dismiss();
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



    private void deletePdf(int pos) {
        UploadPdfFiles uploadFile = uploadFilesList.get(pos);
        //String pdfURL = uploadFile.getUrl();
        final String pdfID = uploadFile.getId();
        String storageFileName = uploadFile.getStorageFileName();

        StorageReference deleteRef = storageReference.child(storageFileName);
        deleteRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                databaseReferenceSemesters.child(pdfID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Successfully deleted from storage and database", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "why man", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void readPdf(int pos) {
        UploadPdfFiles uploadFile = uploadFilesList.get(pos);
        String url = uploadFile.getUrl();
        Intent intent = new Intent(context, ReadPdfActivity.class);
        intent.putExtra("message",url);
        startActivity(intent);
    }

    private void showAllPDFs() {
        databaseReferenceSemesters.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    UploadPdfFiles uploadFiles = dataSnapshot.getValue(UploadPdfFiles.class);
                    uploadFilesList.add(uploadFiles);
                }

                String[] uploads = new String[uploadFilesList.size()];
                for (int i=0; i<uploads.length; i++){
                    uploads[i] = uploadFilesList.get(i).getName();
                }

                arrayAdapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, uploads){
                    @NonNull
                    @Override
                    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView textView = view.findViewById(android.R.id.text1);
                        textView.setTextColor(Color.BLACK);
                        textView.setTextSize(20);
                        return view;
                    }
                };
                listView.setAdapter(arrayAdapter);
                registerForContextMenu(listView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
