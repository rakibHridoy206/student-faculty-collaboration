package com.example.studentteachercollaborations.StudentsPortal;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.studentteachercollaborations.FacultyPanel.UploadPdfFiles;
import com.example.studentteachercollaborations.R;
import com.example.studentteachercollaborations.ReadPdfActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentsBooksShow extends Fragment {
    private Context context;
    private ListView listView;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceFirstSemester;
    private List<UploadPdfFiles> uploadFilesList;

    public StudentsBooksShow() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_students_books, container, false);
        listView = view.findViewById(R.id.pdfListViewStudents);

        Bundle bundle = this.getArguments();
        final String data = bundle.getString("key");

        databaseReference = FirebaseDatabase.getInstance().getReference("UploadedBooksLinks");
        databaseReferenceFirstSemester = databaseReference.child(data);

        uploadFilesList = new ArrayList<>();
        showAllPDFs();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.layout_select_type_pdf_students);

                final int pos = position;
                final FloatingActionButton favCancel;
                final TextView download, viewPDF;

                favCancel = dialog.findViewById(R.id.cancelStudents);
                download = dialog.findViewById(R.id.downloadBook);
                viewPDF = dialog.findViewById(R.id.openPdfStudent);

                favCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UploadPdfFiles uploadPdfFiles = uploadFilesList.get(pos);
                        Intent intent = new Intent();
                        intent.setDataAndType(Uri.parse(uploadPdfFiles.getUrl()), Intent.ACTION_VIEW);
                        startActivity(intent);
                    }
                });

                viewPDF.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UploadPdfFiles uploadFile = uploadFilesList.get(pos);
                        String url = uploadFile.getUrl();
                        Intent intent = new Intent(v.getContext(), ReadPdfActivity.class);
                        intent.putExtra("message",url);
                        v.getContext().startActivity(intent);
                    }
                });
                dialog.show();
            }
        });
        return view;
    }

    private void showAllPDFs() {
        databaseReferenceFirstSemester.addValueEventListener(new ValueEventListener() {
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

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, uploads){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView textView = view.findViewById(android.R.id.text1);
                        textView.setTextColor(Color.BLACK);
                        textView.setTextSize(20);
                        return view;
                    }
                };
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
