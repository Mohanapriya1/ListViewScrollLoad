package com.sample.listviewscrollloading;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAdapter extends BaseAdapter {
    private Activity activity;
    private List<ListModel> listModels;

    ListAdapter(MainActivity mainActivity, List<ListModel> arrayList) {
        this.activity = mainActivity;
        this.listModels = arrayList;
    }

    @Override
    public int getCount() {
        return listModels.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(activity).
                    inflate(R.layout.listitem, parent, false);
        }

        TextView text_title = convertView.findViewById(R.id.text_title);
        ImageView image_thumb = convertView.findViewById(R.id.image);
        String title =listModels.get(position).getTitle();
        text_title.setText(title);
        String thumb_url = listModels.get(position).getThumbnailUrl();
        Picasso.get()
                .load(thumb_url)
               // .networkPolicy(NetworkPolicy.NO_CACHE)

                .into(image_thumb);
        return convertView;
    }

    void filterList(List<ListModel> filterList) {
        listModels = filterList;
        notifyDataSetChanged();
    }
}
