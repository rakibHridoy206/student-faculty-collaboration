package com.example.studentteachercollaborations.StudentsPortal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentteachercollaborations.CommonFeatures.Questions.AnswersAdapter;
import com.example.studentteachercollaborations.CommonFeatures.Questions.CommentInfo;
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
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class AnswerPageStudent extends Fragment {
    private Context context;
    private ImageView imageView;
    private TextView postUserName, postTime, postTitle, postDescription;
    private RecyclerView recyclerView;
    private EditText commentEditText;
    private DatabaseReference userRef;
    private DatabaseReference databaseReferenceQuestions;
    private DatabaseReference databaseReferenceCommentsWay;
    private String questionID;
    private FirebaseUser currentUser;
    private AnswersAdapter answersAdapter;

    public AnswerPageStudent() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_questions_and_answers, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null){
            questionID = bundle.getString("id");
        }

        requireActivity().setTitle("QUESTIONS & ANSWERS");
        userRef = FirebaseDatabase.getInstance().getReference("STUDENT_INFO");
        databaseReferenceQuestions = FirebaseDatabase.getInstance().getReference("QuestionAnswers");
        DatabaseReference databaseReferenceComments = databaseReferenceQuestions.child(questionID);
        databaseReferenceCommentsWay = databaseReferenceComments.child("Comments");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        imageView = view.findViewById(R.id.profileShowQuestionView);
        postUserName = view.findViewById(R.id.userNameQuestionsView);
        postTime = view.findViewById(R.id.userUploadTimeQuestionsView);
        postTitle = view.findViewById(R.id.questionTitleView);
        postDescription = view.findViewById(R.id.quesionDetailsView);
        recyclerView = view.findViewById(R.id.commentsRecyclerView);
        commentEditText = view.findViewById(R.id.answerDetailsEditText);
        TextView postComment = view.findViewById(R.id.postQuestionButton);

        showQuestionInformation();

        postComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentUpload();
            }
        });

        databaseReferenceCommentsWay.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<CommentInfo> commentInfos = new ArrayList<>();
                for(DataSnapshot d: snapshot.getChildren()){
                    CommentInfo commentInfo = d.getValue(CommentInfo.class);
                    commentInfos.add(commentInfo);
                }
                answersAdapter = new AnswersAdapter(context, commentInfos);
                LinearLayoutManager llm = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(answersAdapter);
                answersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

    private void showQuestionInformation() {
        databaseReferenceQuestions.child(questionID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object imageUrl, userName, time, title, postDes;
                imageUrl = snapshot.child("questionUserUrl").getValue();
                userName = snapshot.child("questionUserName").getValue();
                time = snapshot.child("questionTime").getValue();
                title = snapshot.child("questionTitle").getValue();
                postDes = snapshot.child("questionDescription").getValue();

                Picasso.with(context).load(String.valueOf(imageUrl)).into(imageView);
                postUserName.setText(String.valueOf(userName));
                postTime.setText(String.valueOf(time));
                postTitle.setText(String.valueOf(title));
                postDescription.setText(String.valueOf(postDes));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    postDescription.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void commentUpload() {
        if (commentEditText.getText() != null){
            final String comment = commentEditText.getText().toString();
            final String commentId = databaseReferenceCommentsWay.push().getKey();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MMM-dd 'at' hh:mm:ss aa");
            dateTimeInGMT.setTimeZone(TimeZone.getTimeZone("GMT"));
            final String time = dateTimeInGMT.format(new Date());

            if (currentUser != null){
                final String userId = currentUser.getUid();
                userRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Object name = snapshot.child("facultyName").getValue();
                        Object url = snapshot.child("facultyImageUrl").getValue();

                        CommentInfo commentInfo = new CommentInfo(commentId, comment, String.valueOf(name),String.valueOf(url),time);
                        if (commentId != null){
                            databaseReferenceCommentsWay.child(commentId).setValue(commentInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(context, "Posted", Toast.LENGTH_LONG).show();
                                    commentEditText.setText(null);
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
