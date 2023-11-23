package com.example.servicenovigrad;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BranchServicesList extends ArrayAdapter<String> {
    private Activity context;
    List<String> services;

    public BranchServicesList(Activity context, List<String> services) {
        super(context, R.layout.service_list_layout, services);
        this.context = context;
        this.services = services;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.service_list_layout, null, true);

        TextView serviceNameTextView = (TextView) listViewItem.findViewById(R.id.serviceNameTextView);

        String serviceName = services.get(position);
        serviceNameTextView.setText(serviceName);
        return listViewItem;
    }
}