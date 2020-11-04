package com.example.studentteachercollaborations.StudentsPortal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentteachercollaborations.CommonFeatures.Questions.QuestionsAdapter;
import com.example.studentteachercollaborations.CommonFeatures.Questions.QuestionsInfo;
import com.example.studentteachercollaborations.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class StudentsQuestions extends Fragment {
    private Context context;
    private EditText questionTitle, questionDescription;
    private RecyclerView recyclerView;
    private StudentQuestionsAdapter questionsAdapter;
    private OnAskedQuestionStudentListener askedQuestionStudentListener;

    public StudentsQuestions() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        askedQuestionStudentListener = (OnAskedQuestionStudentListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ask_question, container, false);
        requireActivity().setTitle("QUESTIONS");
        final DatabaseReference databaseReferencequestions = FirebaseDatabase.getInstance().getReference("QuestionAnswers");
        final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("STUDENTS_INFO");
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        questionTitle = view.findViewById(R.id.askQuestionTitleEditText);
        questionDescription = view.findViewById(R.id.askQuestionDetailsEditText);
        recyclerView = view.findViewById(R.id.questionsRecyclerView);
        TextView postBtn = view.findViewById(R.id.postQuestionButton);

        databaseReferencequestions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<QuestionsInfo> infos = new ArrayList<>();
                for(DataSnapshot d: snapshot.getChildren()){
                    QuestionsInfo questionsInfo = d.getValue(QuestionsInfo.class);
                    infos.add(questionsInfo);
                }
                questionsAdapter = new StudentQuestionsAdapter(context, infos, askedQuestionStudentListener);
                LinearLayoutManager llm = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(questionsAdapter);
                questionsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionTitle.getText() != null && questionDescription.getText() != null){
                    final String title = questionTitle.getText().toString();
                    final String des = questionDescription.getText().toString();
                    final String questionID = databaseReferencequestions.push().getKey();
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MMM-dd 'at' hh:mm:ss aa");
                    dateTimeInGMT.setTimeZone(TimeZone.getTimeZone("GMT"));
                    final String time = dateTimeInGMT.format(new Date());

                    if (currentUser!= null){
                        final String userID = currentUser.getUid();
                        if (userID.isEmpty()){
                            Toast.makeText(context, "System cannot find your id", Toast.LENGTH_SHORT).show();
                        }else {
                            userRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Object name = snapshot.child("studentName").getValue();
                                    Object url = snapshot.child("imageUrl").getValue();
                                    String n = String.valueOf(name);
                                    String furl = String.valueOf(url);

                                    QuestionsInfo questionsInfo = new QuestionsInfo(questionID, title, des, n, furl, time);

                                    if (questionID != null){
                                        databaseReferencequestions.child(questionID).setValue(questionsInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(context, "Posted", Toast.LENGTH_LONG).show();
                                                questionTitle.setText(null);
                                                questionDescription.setText(null);
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }

                }
            }
        });

        return view;
    }

    public interface OnAskedQuestionStudentListener{
        void askedQuestionStudentSuccessful(String id);
    }
}
