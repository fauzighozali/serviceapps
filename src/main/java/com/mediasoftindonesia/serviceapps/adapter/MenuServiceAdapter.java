package com.mediasoftindonesia.serviceapps.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mediasoftindonesia.serviceapps.R;
import com.mediasoftindonesia.serviceapps.activity.TokoActivity;
import com.mediasoftindonesia.serviceapps.model.PostResponseService;

import java.util.List;

public class MenuServiceAdapter extends RecyclerView.Adapter<MenuServiceAdapter.MyViewHolder> {

    private List<PostResponseService> dataModelArrayList;

    public MenuServiceAdapter(List<PostResponseService> dataModelArrayList) {
        this.dataModelArrayList = dataModelArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_menu_service_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final PostResponseService postResponseData = dataModelArrayList.get(position);

        holder.tvName.setText(postResponseData.getService_name());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TokoActivity.class);
                intent.putExtra("service_id", postResponseData.getId_service());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.text_view_service_name);
        }
    }

}
