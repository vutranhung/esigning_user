package vn.com.japfa.esigning_user.documents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import vn.com.japfa.esigning_user.R;


public class AdapterExpandableListMenu extends BaseExpandableListAdapter {
    private Context mContext;
    private List<String> mGroups;
    private HashMap<String, List<String>> mItems;


    public AdapterExpandableListMenu(Context mContext, List<String> mGroups, HashMap<String, List<String>> mItems) {
        this.mContext = mContext;
        this.mGroups = mGroups;
        this.mItems = mItems;
    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mItems.get(mGroups.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return mGroups.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mItems.get(mGroups.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_expandablelistview_group, null);
        }
        String expandedListGroup = (String) getGroup(i);
        TextView textViewExpandedGroups = view.findViewById(R.id.textViewExpandedGroups);
        textViewExpandedGroups.setText(expandedListGroup);

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_expandablelistview_item, null);
        }
        String expandableItem = (String) getChild(i, i1);
        TextView textViewExpandedItems = view.findViewById(R.id.textViewExpandedItems);
        textViewExpandedItems.setText(expandableItem);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
