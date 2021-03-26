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

public class BinomialCustomList extends ArrayAdapter<Binomial> {
    private ArrayList<Binomial> trials;
    private Context context;

    public BinomialCustomList(Context context, ArrayList<Binomial> trials){
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
            view = LayoutInflater.from(context).inflate(R.layout.content_binomial, parent,false);
        }
        Binomial binomial = trials.get(position);
        TextView resultView = view.findViewById(R.id.result);
        TextView nameView = view.findViewById(R.id.experimenter);

        String result = binomial.getResult();
        String experimenterName = binomial.getExperimenter();

        resultView.setText(result);
        nameView.setText(experimenterName);

        return view;
    }
}

