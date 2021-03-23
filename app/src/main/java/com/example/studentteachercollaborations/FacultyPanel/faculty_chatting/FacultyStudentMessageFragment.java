package com.example.studentteachercollaborations.FacultyPanel.faculty_chatting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.studentteachercollaborations.FacultyPanel.FacultyInfo;
import com.example.studentteachercollaborations.R;
import com.example.studentteachercollaborations.StudentsPortal.student_chatting.SenderReciverPojo;
import com.example.studentteachercollaborations.utils.HelperUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FacultyStudentMessageFragment extends Fragment {
    public static String receiverID;
    public static String receiverName;
    public static String receiverImage;
    public static int position = -1;

    ImageButton sendMsgBtn;
    EditText msgET;
    RecyclerView msgRV;
    Toolbar toolbar;
    ImageView prfileImage;
    TextView nameTV, statusTV;

    FacultyInfo facultyInfo;

    private DatabaseReference facultyReference;
    private DatabaseReference studentReference;
    private FirebaseAuth firebaseAuth;
    DatabaseReference  userRef;
    DatabaseReference  userID;
    DatabaseReference reciverIDRef;
    DatabaseReference  chatListRef;
    DatabaseReference  receiverChatListRef;
    FirebaseUser firebaseUser;
    FirebaseUser currentUser;

    public FacultyStudentMessageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_faculty_student_message, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        facultyReference = FirebaseDatabase.getInstance().getReference("FACULTY_INFO");
        studentReference = FirebaseDatabase.getInstance().getReference("STUDENTS_INFO");
        currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null){
            userID = facultyReference.child(currentUser.getUid());
        }
        chatListRef = userID.child("ChatList").child(receiverID);

        sendMsgBtn = view.findViewById(R.id.sentMsgBtn);
        msgET = view.findViewById(R.id.messageET);
        msgRV = view.findViewById(R.id.messageRV);
        prfileImage = view.findViewById(R.id.faculty_message_profile_image);
        nameTV = view.findViewById(R.id.nameTV);
        statusTV = view.findViewById(R.id.statusTV);
        toolbar = view.findViewById(R.id.faculty_toolbar_message);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment chattingFragment = new FacultyChatHomeFragment();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.facultyFragmentContainer, chattingFragment).commit();
            }
        });

        try {
            nameTV.setText(receiverName);

            //Picasso.get().load(reciverImage).into(prfileImage);
            Glide.with(requireActivity())
                    .load(receiverImage)
                    .placeholder(R.drawable.ic_perm_)
                    .into(prfileImage);

            userRef = facultyReference.child(currentUser.getUid());
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    facultyInfo = dataSnapshot.getValue(FacultyInfo.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        sendMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = msgET.getText().toString().trim();

                if (message.equals("")) {
                    msgET.setError("Write something");
                } else{

                    String chatID = chatListRef.push().getKey();
                    String senderID = firebaseAuth.getUid();

                    SenderReciverPojo senderReciverPojo = new SenderReciverPojo(
                            chatID, message, senderID,
                            receiverID, receiverName, receiverImage,
                            facultyInfo.getFacultyName(), facultyInfo.getFacultyImageUrl(),
                            HelperUtils.getDateWithTime(), 0);

                    chatListRef.child(chatID).setValue(senderReciverPojo)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            });

                    reciverIDRef = studentReference.child(senderReciverPojo.getReciverID());
                    receiverChatListRef = reciverIDRef.child("ChatList").child(senderReciverPojo.getSenderID());
                    senderReciverPojo.setStatus(HelperUtils.getDateWithTime());
                    receiverChatListRef.child(chatID).setValue(senderReciverPojo)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            });

                    msgET.setText("");
                }
            }
        });

        try {
            chatListRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<SenderReciverPojo> contractPojoList = new ArrayList<>();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        contractPojoList.add(data.getValue(SenderReciverPojo.class));
                    }

                    FacultyStudentMessageAdapter facultyStudentMessageAdapter = new FacultyStudentMessageAdapter(contractPojoList, getActivity());
                    LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                    llm.setStackFromEnd(true);
                    llm.setOrientation(LinearLayoutManager.VERTICAL);

                    msgRV.setLayoutManager(llm);
                    msgRV.setAdapter(facultyStudentMessageAdapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}