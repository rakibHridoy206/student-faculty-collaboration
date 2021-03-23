package com.example.studentteachercollaborations.FacultyPanel.faculty_chatting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentteachercollaborations.R;
import com.example.studentteachercollaborations.StudentsPortal.student_chatting.FacultyChatListAdapter;
import com.example.studentteachercollaborations.StudentsPortal.student_chatting.SenderReciverPojo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StudentChatListFragment extends Fragment {
    private RecyclerView msgRV;
    private DatabaseReference facultyReference;
   // private DatabaseReference studentReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private DatabaseReference  userID;
    private DatabaseReference  chatListRef;

    public StudentChatListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_chat_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        facultyReference = FirebaseDatabase.getInstance().getReference("FACULTY_INFO");
        //studentReference = FirebaseDatabase.getInstance().getReference("STUDENTS_INFO");
        currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null){
            userID = facultyReference.child(currentUser.getUid());
        }
        chatListRef = userID.child("ChatList");

        msgRV = view.findViewById(R.id.student_chat_list_rv);

        chatListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<SenderReciverPojo> contractPojoList = new ArrayList<>();
                List<SenderReciverPojo> finalConractList = new ArrayList<>();

                SenderReciverPojo senderReciverPojo;
                for (DataSnapshot data : snapshot.getChildren()) {
                    //find specific user msg
                    for (DataSnapshot d : data.getChildren()) {

                        contractPojoList.add(d.getValue(SenderReciverPojo.class));
                    }
                    senderReciverPojo = contractPojoList.get(contractPojoList.size() - 1);
                    //only save the last msg
                    finalConractList.add(senderReciverPojo);
                    contractPojoList.clear();
                }

                Collections.sort(finalConractList, new Comparator<SenderReciverPojo>() {
                    public int compare(SenderReciverPojo s1, SenderReciverPojo s2) {
                        return s1.getStatus().compareToIgnoreCase(s2.getStatus());
                    }
                });

                Collections.reverse(finalConractList);
                buildRV(finalConractList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void buildRV(List<SenderReciverPojo> finalConractList) {
        StudentChatListAdapter chatFriendListAdaper = new StudentChatListAdapter(finalConractList, getActivity());
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        msgRV.setLayoutManager(llm);
        msgRV.setAdapter(chatFriendListAdaper);
    }
}