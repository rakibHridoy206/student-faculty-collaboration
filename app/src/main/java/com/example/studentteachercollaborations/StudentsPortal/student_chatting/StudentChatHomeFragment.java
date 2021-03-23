package com.example.studentteachercollaborations.StudentsPortal.student_chatting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.studentteachercollaborations.R;
import com.google.android.material.tabs.TabLayout;

public class StudentChatHomeFragment extends Fragment {

    public StudentChatHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chatting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        FacultyChatListFragment chatsPeopleFragment = new FacultyChatListFragment();
        FacultyContactListFragment facultyContactListFragment = new FacultyContactListFragment();

        tabLayout.setupWithViewPager(viewPager);

        StudentViewPagerAdapter studentViewPagerAdapter = new StudentViewPagerAdapter(getChildFragmentManager(), 1);
        studentViewPagerAdapter.addFragments(chatsPeopleFragment, "Chats");
        studentViewPagerAdapter.addFragments(facultyContactListFragment, "Faculties");
        viewPager.setAdapter(studentViewPagerAdapter);
    }
}