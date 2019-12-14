package com.mediasoftindonesia.serviceapps.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mediasoftindonesia.serviceapps.R;
import com.mediasoftindonesia.serviceapps.adapter.MenuServiceAdapter;
import com.mediasoftindonesia.serviceapps.model.PostResponseService;
import com.mediasoftindonesia.serviceapps.network.ApiService;
import com.mediasoftindonesia.serviceapps.network.RetrofitBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "HomeFragment";

    private MenuServiceAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private ApiService service;
    private Call<List<PostResponseService>> call;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.activity_home_fragment, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        service = RetrofitBuilder.createService(ApiService.class);
        getData();

        return view;
    }

    private void getData() {
        call = service.postsDataService();
        call.enqueue(new Callback<List<PostResponseService>>() {
            @Override
            public void onResponse(Call<List<PostResponseService>> call, Response<List<PostResponseService>> response) {
                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    List<PostResponseService> responseDataList = response.body();
                    adapter = new MenuServiceAdapter(responseDataList);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<PostResponseService>> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
