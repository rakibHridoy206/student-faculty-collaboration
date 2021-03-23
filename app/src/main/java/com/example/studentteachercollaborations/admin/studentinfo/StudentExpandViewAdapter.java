package com.example.studentteachercollaborations.admin.studentinfo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;

import com.example.studentteachercollaborations.R;

import java.util.HashMap;
import java.util.List;

public class StudentExpandViewAdapter extends BaseExpandableListAdapter {
    private final List<String> listDataHeader;
    private final HashMap<String, List<String>> listHashMap;

    public StudentExpandViewAdapter(List<String> listDataHeader, HashMap<String, List<String>> listHashMap) {
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listHashMap.get(listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listHashMap.get(listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String title = (String) getGroup(groupPosition);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_parent, parent, false);
        TextView groupName = view.findViewById(R.id.groupNameTV);
        AppCompatImageView imageView = view.findViewById(R.id.groupImage);
        groupName.setText(title);
        imageView.setSelected(isExpanded);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_child, parent, false);
        TextView childName = convertView.findViewById(R.id.childNameTV);
        childName.setText((String) getChild(groupPosition, childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
