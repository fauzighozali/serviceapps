package com.mediasoftindonesia.serviceapps;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class HomeFragment extends android.support.v4.app.Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MenuServiceAdapter adapter;
    private ArrayList<Service> servicesArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.activity_home_fragment, container, false);

        addData();
        recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new MenuServiceAdapter(servicesArrayList);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void addData() {
        servicesArrayList = new ArrayList<>();
        servicesArrayList.add(new Service("Service Radio"));
        servicesArrayList.add(new Service("Service TV"));
        servicesArrayList.add(new Service("Service Kulkas"));
        servicesArrayList.add(new Service("Service Mesin Cuci"));
        servicesArrayList.add(new Service("Service AC"));
        servicesArrayList.add(new Service("Service Kipas Angin"));
        servicesArrayList.add(new Service("Service Setrika"));
        servicesArrayList.add(new Service("Service Dispenser"));
    }
}
