package com.test.serachapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExperimentalAdapter extends RecyclerView.Adapter<ExperimentalAdapter.ViewHolder> {

    Context context;
    List<Experimental> datas;
    public ExperimentalAdapter(Context context, List<Experimental> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(datas.get(position).getName());
        holder.tvStatus.setText(datas.get(position).getStatus());
        holder.tvDesc.setText(datas.get(position).getDesc());
    }



    @Override
    public int getItemCount() {
        return datas.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName;
        private TextView tvDesc;
        private TextView tvStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
