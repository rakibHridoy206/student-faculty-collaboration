package com.example.studentteachercollaborations.CommonFeatures.Thesis;

import android.content.Context;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ThesisFragment extends Fragment {
    private Context context;
    private RecyclerView recyclerView;
    private OnAddThesisClick addThesisClick;
    private ThesisAdapter adapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        addThesisClick = (OnAddThesisClick) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thesis, container, false);
        requireActivity().setTitle("RESEARCH PAPERS");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ThesisInformation");
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        FloatingActionButton fab = view.findViewById(R.id.addThesisFab);
        recyclerView = view.findViewById(R.id.thesisRV);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<ThesisInfo> thesisInfos = new ArrayList<>();
                for(DataSnapshot d: snapshot.getChildren()){
                    ThesisInfo thesisInfo = d.getValue(ThesisInfo.class);
                    thesisInfos.add(thesisInfo);
                }
                adapter = new ThesisAdapter(context, thesisInfos);
                LinearLayoutManager llm = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(adapter);
                //adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addThesisClick.onAddThesisSuccessful();
            }
        });
        return view;
    }

    public interface OnAddThesisClick{
        void onAddThesisSuccessful();
    }
}
