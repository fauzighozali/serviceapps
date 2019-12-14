package com.mediasoftindonesia.serviceapps.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mediasoftindonesia.serviceapps.R;
import com.mediasoftindonesia.serviceapps.model.History;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private List<History> dataModelArrayList;

    public HistoryAdapter(List<History> dataModelArrayList) {
        this.dataModelArrayList = dataModelArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_history_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        History history = dataModelArrayList.get(position);

        holder.tvName.setText(history.getStore().getName_store());
        holder.tvTgl.setText(history.getTgl_service());
        holder.tvCatatan.setText(history.getCatatan_service());
        holder.tvStatus.setText(history.getStatus().getNama_status());
    }

    @Override
    public int getItemCount() {
        return (dataModelArrayList != null) ? dataModelArrayList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvTgl, tvCatatan, tvStatus;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.text_view_histori_name_store);
            tvTgl = itemView.findViewById(R.id.text_view_histori_booking_date);
            tvCatatan = itemView.findViewById(R.id.text_view_histori_catatan_service);
            tvStatus = itemView.findViewById(R.id.text_view_histori_status_service);
        }
    }

}
