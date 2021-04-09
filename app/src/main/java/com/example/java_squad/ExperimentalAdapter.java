package com.example.java_squad;

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
    private OnNoteListener monNoteListener;


    public ExperimentalAdapter(Context context, List<Experimental> datas,OnNoteListener onNoteListener) {
        this.context = context;
        this.datas = datas;
        this.monNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item, null);
        return new ViewHolder(view,monNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(datas.get(position).getName());
        if (datas.get(position).getActive())
            holder.tvStatus.setText("In Progress");
        else
            holder.tvStatus.setText("Ended");
        holder.tvOwner.setText(datas.get(position).getOwner().getUsername());
        holder.tvDesc.setText(datas.get(position).getDescription());

    }



    @Override
    public int getItemCount() {
        return datas.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvName;
        private TextView tvDesc;
        private TextView tvStatus;
        private TextView tvOwner;
        OnNoteListener onNoteListener;

        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvOwner = itemView.findViewById(R.id.tvOwner);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClick( int position);
    }
}
