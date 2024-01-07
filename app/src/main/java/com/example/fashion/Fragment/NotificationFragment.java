package com.example.fashion.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashion.Adapter.NotificationAdapter;
import com.example.fashion.Domain.NotificationDomain;
import com.example.fashion.Helper.ManagmentNotifications;
import com.example.fashion.Helper.TinyDB;
import com.example.fashion.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {
    private RecyclerView notificationsRecyclerView;
    private TinyDB tinyDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        initView(view); // Initialize views here
        return view;
    }

    private void initView(View view) {
        notificationsRecyclerView = view.findViewById(R.id.listview);

        tinyDB = new TinyDB(getActivity().getApplicationContext());
        List<NotificationDomain> notifications = tinyDB.getListObjectNotification("Notifications");
        ManagmentNotifications managmentNotifications = new ManagmentNotifications(getActivity().getApplicationContext());
//        List<NotificationDomain> notifications = managmentNotifications.getUnreadNotifications();
        NotificationAdapter adapter = new NotificationAdapter(notifications);
        Toast.makeText(getActivity().getApplicationContext(),"Notifications Count is :"+notifications.size(), Toast.LENGTH_SHORT).show();
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        notificationsRecyclerView.setAdapter(adapter);
    }
}