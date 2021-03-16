package com.pha.likaijie.adapter;

import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.databinding.DataBindingUtil;

import com.pha.likaijie.R;
import com.pha.likaijie.bean.BoxBean;
import com.pha.likaijie.bean.OrdBean;
import com.pha.likaijie.common.BaseApplication;
import com.pha.likaijie.databinding.ItemBoxBinding;


import java.util.List;


/**
 * Created by yaoxin on 2019/12/19.
 */
public class QueryOrdAdapter implements ExpandableListAdapter{
    private List<OrdBean> mList;
    private ItemBoxBinding mMainBinding;

    //private ItemBoxDetailBinding mDetailBinding;

    public QueryOrdAdapter(List<OrdBean> list) {
        mList = list;

    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return mList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mList.get(groupPosition).getRows().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mList.get(groupPosition).getRows().get(childPosition);
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
        if (convertView == null) {
            mMainBinding = DataBindingUtil.inflate(LayoutInflater.from(BaseApplication.context), R.layout.item_box, parent, false);
            convertView = mMainBinding.getRoot();
            convertView.setTag(mMainBinding);
        } else {
            mMainBinding = (ItemBoxBinding) convertView.getTag();
        }
        mMainBinding.setContent(mList.get(groupPosition));


        return convertView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }




}


