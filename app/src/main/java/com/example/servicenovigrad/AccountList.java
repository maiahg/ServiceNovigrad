package com.example.servicenovigrad;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AccountList extends ArrayAdapter<User> {
    private Activity context;
    List<User> users;

    AccountList(Activity context, List<User> users) {
        super(context, R.layout.account_list_layout, users);
        this.context = context;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.account_list_layout, null, true);

        TextView accountName = listViewItem.findViewById(R.id.fullName);
        TextView accountRole = listViewItem.findViewById(R.id.roleAccount);

        User user = users.get(position);
        accountName.setText(user.getFirstName() + " " + user.getLastName());
        accountRole.setText(user.getRole());
        return listViewItem;
    }
}
