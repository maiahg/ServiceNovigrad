package com.example.servicenovigrad;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RequestsList extends ArrayAdapter<String> {
    private Activity context;
    List<String> requests;

    public RequestsList(Activity context, List<String> requests) {
        super(context, R.layout.service_list_layout, requests);
        this.context = context;
        this.requests = requests;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.service_list_layout, null, true);

        TextView requestNameTextView = listViewItem.findViewById(R.id.serviceNameTextView);

        String serviceName = requests.get(position);
        requestNameTextView.setText(serviceName);
        return listViewItem;
    }

}
