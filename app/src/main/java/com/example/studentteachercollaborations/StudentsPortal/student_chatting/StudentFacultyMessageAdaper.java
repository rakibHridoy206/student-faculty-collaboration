package com.example.studentteachercollaborations.StudentsPortal.student_chatting;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.studentteachercollaborations.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class StudentFacultyMessageAdaper extends RecyclerView.Adapter<StudentFacultyMessageAdaper.ContractViewHolder> {
    List<SenderReciverPojo> list;
    Context context;
    public  static  final int MSG_TYPE_LEFT = 0;
    public  static  final int MSG_TYPE_RIGHT = 1;
    String firebaseUser;

    public StudentFacultyMessageAdaper(List<SenderReciverPojo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ContractViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType==MSG_TYPE_RIGHT)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(R.layout.chat_item_right, parent,false);
            return new ContractViewHolder(view);
        }
        else
        {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(R.layout.chat_item_left,parent,false);
            return new ContractViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final ContractViewHolder holder, final int position) {

            holder.msgTV.setVisibility(View.VISIBLE);
            //holder.imageView.setVisibility(View.GONE);
            holder.msgTV.setText(list.get(position).getMsg());
            holder.timeTV.setText(list.get(position).getStatus());
            Log.i(TAG, "onBindViewHolder: "+list.get(position).getSenderImage()+" \n"+list.get(position).getReciverImage());

       //    Picasso.with(context).load(list.get(position).getSenderImage()).into(holder.imageView);
            Glide.with(context)
                    .load(list.get(position).getSenderImage())
                    .centerCrop()
                    .placeholder(R.drawable.background)
                    .into(holder.imageView);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

   public static class ContractViewHolder extends RecyclerView.ViewHolder{

       ImageView imageView;
        TextView msgTV,timeTV;

        public ContractViewHolder(@NonNull View itemView) {
            super(itemView);

            msgTV = itemView.findViewById(R.id.showmsg);
            imageView = itemView.findViewById(R.id.user_image);
            timeTV = itemView.findViewById(R.id.timeTV);


        }
    }

    @Override
    public int getItemViewType(int position) {

         firebaseUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

         if (firebaseUser.equals(list.get(position).getSenderID()))
         {
             return MSG_TYPE_RIGHT;
         }
         else
         {
             return MSG_TYPE_LEFT;
         }

    }
}
