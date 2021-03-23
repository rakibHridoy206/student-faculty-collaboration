package com.example.studentteachercollaborations.StudentsPortal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studentteachercollaborations.CommonFeatures.Questions.QuestionsInfo;
import com.example.studentteachercollaborations.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StudentQuestionsAdapter extends RecyclerView.Adapter<StudentQuestionsAdapter.ViewHolder> {
    private Context context;
    private List<QuestionsInfo> questionsInfoList;
    private StudentsQuestions.OnAskedQuestionStudentListener askedQuestionStudentListener;

    public StudentQuestionsAdapter(Context context, List<QuestionsInfo> questionsInfoList,
                                   StudentsQuestions.OnAskedQuestionStudentListener askedQuestionStudentListener) {
        this.context = context;
        this.questionsInfoList = questionsInfoList;
        this.askedQuestionStudentListener = askedQuestionStudentListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_questions, parent, false);
        return new StudentQuestionsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.nameTV.setText(questionsInfoList.get(position).getQuestionUserName());
        holder.timeTV.setText(questionsInfoList.get(position).getQuestionTime());
        holder.titleTV.setText(questionsInfoList.get(position).getQuestionTitle());
        Picasso.with(context).load(questionsInfoList.get(position).getQuestionUserUrl()).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askedQuestionStudentListener.askedQuestionStudentSuccessful(questionsInfoList.get(position).getQuestionID());
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionsInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView titleTV, nameTV, timeTV;
        private CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardQuestions);
            timeTV = itemView.findViewById(R.id.userUploadTimeQuestions);
            nameTV = itemView.findViewById(R.id.userNameQuestions);
            titleTV = itemView.findViewById(R.id.quesionTitle);
            imageView = itemView.findViewById(R.id.profileShowQuestion);
        }
    }
}
