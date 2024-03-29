package com.mediasoftindonesia.serviceapps.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.mediasoftindonesia.serviceapps.R;
import com.mediasoftindonesia.serviceapps.adapter.TokoAdapter;
import com.mediasoftindonesia.serviceapps.model.PostResponseStore;
import com.mediasoftindonesia.serviceapps.model.Store;
import com.mediasoftindonesia.serviceapps.network.ApiService;
import com.mediasoftindonesia.serviceapps.network.RetrofitBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokoActivity extends AppCompatActivity {

    private static final String TAG = "TokoActivity";
    private TokoActivity self;

    private TokoAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private ApiService service;
    private Call<PostResponseStore> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko);
        self = this;

        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(self);
        recyclerView.setLayoutManager(layoutManager);

        service = RetrofitBuilder.createService(ApiService.class);
        getData();
    }

    private void getData() {
        Intent intent = getIntent();

        call = service.postsDataStore(intent.getIntExtra("service_id",0));
        call.enqueue(new Callback<PostResponseStore>() {
            @Override
            public void onResponse(Call<PostResponseStore> call, Response<PostResponseStore> response) {
                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    List<Store> responseDataList = response.body().getStore();
                    adapter = new TokoAdapter(responseDataList);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<PostResponseStore> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });

    }
}
