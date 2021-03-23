package com.example.studentteachercollaborations.StudentsPortal.student_chatting;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentteachercollaborations.FacultyPanel.FacultyInfo;
import com.example.studentteachercollaborations.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FacultyContactListFragment extends Fragment {
    FloatingActionButton searchActionButton;
    RecyclerView contractRV ;
    EditText searchET;
    FacultyContactListAdapter allContractListAdaper;
    //List<UserInformationPojo> contractPojoList = new ArrayList<>();

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    List<FacultyInfo> facultyInfoList = new ArrayList<>();


    public FacultyContactListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_people, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("FACULTY_INFO");

        contractRV = view.findViewById(R.id.fac_contractRV);
        searchET = view.findViewById(R.id.fac_searchET);
        searchActionButton = view.findViewById(R.id.fac_searchActionButton);

        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        searchActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchET.animate().alpha(1.0f).setDuration(2000);;
                searchET.setVisibility(View.VISIBLE);
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                facultyInfoList.clear();

                for (DataSnapshot data: snapshot.getChildren()){
                    facultyInfoList.add(data.getValue(FacultyInfo.class));
                }
                allContractListAdaper = new FacultyContactListAdapter(facultyInfoList, getActivity());
                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                contractRV.setLayoutManager(llm);
                contractRV.setAdapter(allContractListAdaper);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void filter(String text) {
        ArrayList<FacultyInfo> filteredList = new ArrayList<>();
        for (FacultyInfo contractPojo : facultyInfoList) {
            if (contractPojo.getFacultyName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(contractPojo);
            }
        }
        allContractListAdaper.filterList(filteredList);
    }
}