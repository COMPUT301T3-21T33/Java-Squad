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

public class MeasurementCustomList extends ArrayAdapter<Measurement> {
    private ArrayList<Measurement> trials;
    private Context context;

    public MeasurementCustomList(Context context, ArrayList<Measurement> trials){
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
            view = LayoutInflater.from(context).inflate(R.layout.content, parent,false);
        }
        Measurement measurement = trials.get(position);
        TextView unit = view.findViewById(R.id.unit);
        TextView amount = view.findViewById(R.id.amount);

        double getAmount = measurement.getAmount();
        String amountInString = Double.toString(getAmount);
        unit.setText(measurement.getUnit());
        amount.setText(amountInString);
        return view;
    }
}
