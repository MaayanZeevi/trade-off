package com.example.tradeoff;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AllPostAdapter extends ArrayAdapter<PostView> {
    Context context;
    List<PostView> objects;
    public AllPostAdapter(Context context, int resource, int textViewResourceId, List<PostView> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context=context;
        this.objects=objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_post, parent, false);
        TextView tvTitle = (TextView)view.findViewById(R.id.tvTitle);
        PostView temp = objects.get(position);
        tvTitle.setText(temp.getEmail());
        return view;
    }}