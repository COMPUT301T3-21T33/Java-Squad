package com.example.java_squad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CountCustomList extends ArrayAdapter<Count> {
    private ArrayList<Count> trials;
    private Context context;

    public CountCustomList(Context context, ArrayList<Count> trials){
        super(context,0, trials);
        this.trials = trials;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content_count, parent,false);
        }
        Count count = trials.get(position);
        TextView amount = view.findViewById(R.id.count);
        TextView name = view.findViewById(R.id.experimenter);

        Integer getAmount = count.getCount();
        String amountInString = Integer.toString(getAmount);
        String experimenterName = count.getExperimenter();

        amount.setText(amountInString);
        name.setText(experimenterName);

        return view;
    }
}

