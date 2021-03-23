package com.example.studentteachercollaborations.FacultyPanel.faculty_chatting;

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

public class FacultyChatHomeFragment extends Fragment {

    public FacultyChatHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_faculty_chat_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager viewPager = view.findViewById(R.id.faculty_chat_view_pager);
        TabLayout tabLayout = view.findViewById(R.id.faculty_chat_tab_layout);
        StudentChatListFragment studentChatListFragment = new StudentChatListFragment();
        StudentContactListFragment studentContactListFragment = new StudentContactListFragment();

        tabLayout.setupWithViewPager(viewPager);

        FacultyViewPagerAdapter facultyViewPagerAdapter = new FacultyViewPagerAdapter(getChildFragmentManager(), 1);
        facultyViewPagerAdapter.addFragments(studentChatListFragment, "Chats");
        facultyViewPagerAdapter.addFragments(studentContactListFragment, "Students");
        viewPager.setAdapter(facultyViewPagerAdapter);
    }
}