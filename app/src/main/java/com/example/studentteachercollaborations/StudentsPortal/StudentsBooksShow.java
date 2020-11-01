package com.example.studentteachercollaborations.StudentsPortal;

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
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.example.studentteachercollaborations.FacultyPanel.UploadPdfFiles;
import com.example.studentteachercollaborations.R;
import com.example.studentteachercollaborations.ReadPdfActivity;
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
    private DatabaseReference databaseReferenceFirstSemester;
    private List<UploadPdfFiles> uploadFilesList;
    private String semesterno;

    public StudentsBooksShow() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_students_books, container, false);
        listView = view.findViewById(R.id.pdfListViewStudents);

        Bundle bundle = this.getArguments();
        if (bundle != null){
            final String data = bundle.getString("key");
            semesterno = data;
            requireActivity().setTitle(data+ " SEMESTER BOOKS");
        }


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UploadedBooksLinks");
        databaseReferenceFirstSemester = databaseReference.child(semesterno);

        uploadFilesList = new ArrayList<>();
        showAllPDFs();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                final PopupMenu popup = new PopupMenu(context, view);
                popup.inflate(R.menu.list_menu_students_books);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.readPdfStudent:
                                readPdf(pos);
                                return true;

                            case R.id.downloadBookStudent:
                                downloadPdf(pos);
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

                if (getContext() != null){
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, uploads){
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
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

    private void downloadPdf(int pos) {
        UploadPdfFiles uploadPdfFiles = uploadFilesList.get(pos);
        Intent intent = new Intent();
        intent.setDataAndType(Uri.parse(uploadPdfFiles.getUrl()), Intent.ACTION_VIEW);
        startActivity(intent);
    }
}
