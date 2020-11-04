package com.example.studentteachercollaborations.CommonFeatures.Questions;

import android.content.Context;
import android.os.Build;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentteachercollaborations.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder> {
    private Context context;
    private List<CommentInfo> commentInfoList;

    public AnswersAdapter(Context context, List<CommentInfo> commentInfoList) {
        this.context = context;
        this.commentInfoList = commentInfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_comment, parent, false);
        return new AnswersAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameTV.setText(commentInfoList.get(position).getCommentUserName());
        holder.timeTV.setText(commentInfoList.get(position).getCommentTime());
        holder.comment.setText(commentInfoList.get(position).getCommentDescription());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            holder.comment.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }
        Picasso.with(context).load(commentInfoList.get(position).getCommentUserUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return commentInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView comment, nameTV, timeTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.profileShowComment);
            comment = itemView.findViewById(R.id.commentShow);
            nameTV = itemView.findViewById(R.id.userNameComment);
            timeTV = itemView.findViewById(R.id.userUploadTimeComment);
        }
    }
}
