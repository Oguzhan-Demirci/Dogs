package com.rl.dogs.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.rl.dogs.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BreedAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> breedList = new ArrayList<>();
    private Map<String, ArrayList<String>> breedMap = new HashMap<>();

    public BreedAdapter(Context context, List<String> breedList, Map<String, ArrayList<String>> breedMap){
        this.context = context;
        this.breedList = breedList;
        this.breedMap = breedMap;
    }

    @Override
    public int getGroupCount() {
        return breedList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (breedMap.get(breedList.get(groupPosition)).get(0) != null)
            return breedMap.get(breedList.get(groupPosition)).size();
        else
            return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return breedList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return breedMap.get(breedList.get(groupPosition)).get(childPosition);
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
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.group_item, null);
        TextView textView = convertView.findViewById(R.id.tv_breed);
        textView.setText(breedList.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.child_item, null);
        TextView textView = convertView.findViewById(R.id.tv_sub_breed);
        textView.setText(breedMap.get(breedList.get(groupPosition)).get(childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
