package com.example.servicenovigrad;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ServiceList extends ArrayAdapter<Service> {
    private Activity context;
    List<Service> services;

    public ServiceList(Activity context, List<Service> services) {
        super(context, R.layout.service_list_layout, services);
        this.context = context;
        this.services = services;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.service_list_layout, null, true);

        TextView serviceNameTextView = listViewItem.findViewById(R.id.serviceNameTextView);

        Service service = services.get(position);
        serviceNameTextView.setText(service.getServiceName());
        return listViewItem;
    }
}
