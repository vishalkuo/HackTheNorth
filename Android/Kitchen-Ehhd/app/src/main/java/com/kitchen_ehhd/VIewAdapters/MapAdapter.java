package com.kitchen_ehhd.VIewAdapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.kitchen_ehhd.Models.Drawer;
import com.kitchen_ehhd.Models.DrawerItem;
import com.kitchen_ehhd.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by vishalkuo on 15-09-19.
 */
public class MapAdapter extends BaseAdapter implements Filterable {
    private final ArrayList<DrawerItem> mapData;
    private final ArrayList<DrawerItem> viewData;

    public MapAdapter(List<DrawerItem> map) {
        mapData = (ArrayList<DrawerItem>)map;
        viewData = (ArrayList<DrawerItem>)map;
    }

    @Override
    public int getCount() {
        return viewData.size();
    }
    public void appendToData(String item, int drawerNum) {
        mapData.add(new DrawerItem(item, drawerNum));
    }

    @Override
    public DrawerItem getItem(int i) {
        return viewData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View endView;

        Log.d("TEST", "" + getCount());

        if (view == null){
            endView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_adapter, viewGroup, false);
        } else{
            endView = view;
        }

        DrawerItem items = getItem(i);

        ((TextView)endView.findViewById(R.id.item_name)).setText(items.getName());
        ((TextView)endView.findViewById(R.id.drawer_number)).setText(String.valueOf(items.getDrawerNum()));

        return endView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<DrawerItem> filteredList = new ArrayList<>();
                for (DrawerItem entry : mapData){
                    if (entry.getName().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(entry);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                filterResults.count = filteredList.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                viewData.clear();
                viewData.addAll((List<DrawerItem>) filterResults.values);
                notifyDataSetChanged();

            }
        };
    }
}
