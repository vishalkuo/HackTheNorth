package com.kitchen_ehhd.VIewAdapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.kitchen_ehhd.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by vishalkuo on 15-09-19.
 */
public class MapAdapter extends BaseAdapter implements Filterable {
    private final ArrayList<Map.Entry<String, Integer>> mapData;
    private final ArrayList<Map.Entry<String, Integer>> viewData;

    public MapAdapter(Map<String, Integer> map) {
        mapData = new ArrayList<>();
        viewData = new ArrayList<>();
        mapData.addAll(map.entrySet());
        viewData.addAll(map.entrySet());
    }

    @Override
    public int getCount() {
        return viewData.size();
    }

    @Override
    public Map.Entry<String, Integer> getItem(int i) {
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

        Map.Entry<String, Integer> items = getItem(i);

        ((TextView)endView.findViewById(R.id.item_name)).setText(items.getKey());
        ((TextView)endView.findViewById(R.id.drawer_number)).setText(String.valueOf(items.getValue()));

        return endView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<Map.Entry<String, Integer>> filteredList = new ArrayList<>();
                for (Map.Entry<String, Integer> entry : mapData){
                    if (entry.getKey().toLowerCase().contains(charSequence.toString().toLowerCase())){
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
                viewData.addAll((List<Map.Entry<String, Integer>>) filterResults.values);
//                Log.d("TEST", String.valueOf(viewData.size()));
                notifyDataSetChanged();
            }
        };
    }
}
