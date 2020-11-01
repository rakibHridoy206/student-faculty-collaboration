package com.example.studentteachercollaborations.CommonFeatures.Thesis;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentteachercollaborations.R;
import com.example.studentteachercollaborations.ReadPdfActivity;

import java.util.List;

public class ThesisAdapter extends RecyclerView.Adapter<ThesisAdapter.ViewHolder> {
    private Context context;
    private List<ThesisInfo> thesisInfoList;

    public ThesisAdapter(Context context, List<ThesisInfo> thesisInfoList) {
        this.context = context;
        this.thesisInfoList = thesisInfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_thesis_cardview, parent, false);
        return new ThesisAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.thesisNameTV.setText(thesisInfoList.get(position).getTitle());
        holder.thesisAuthorsTV.setText(thesisInfoList.get(position).getAuthors());
        /*holder.cardView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                final PopupMenu popup = new PopupMenu(context, v);
                popup.inflate(R.menu.list_menu_thesis);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.readThesisPaper:
                                String url = thesisInfoList.get(position).getUrl();
                                readPdf(url);
                                return true;

                            case R.id.downloadThesisPaper:
                                //deletePdf(pos);
                                popup.dismiss();
                                return true;

                            default:
                                return false;
                        }
                    }
                });
            }
        });*/


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popup = new PopupMenu(context, holder.itemView);
                popup.inflate(R.menu.list_menu_thesis);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.readThesisPaper:
                                String url = thesisInfoList.get(position).getUrl();
                                readPdf(url);
                                return true;

                            case R.id.downloadThesisPaper:
                                //deletePdf(pos);
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
    }

    @Override
    public int getItemCount() {
        return thesisInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView thesisNameTV, thesisAuthorsTV;
        private CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thesisNameTV = itemView.findViewById(R.id.thesis_title_Cardview);
            thesisAuthorsTV = itemView.findViewById(R.id.thesis_authors_cardview);
            cardView = itemView.findViewById(R.id.thesisCardViewInfo);
        }
    }

    private void readPdf(String url) {
        Intent intent = new Intent(context, ReadPdfActivity.class);
        intent.putExtra("message",url);
        context.startActivity(intent);
    }
}
