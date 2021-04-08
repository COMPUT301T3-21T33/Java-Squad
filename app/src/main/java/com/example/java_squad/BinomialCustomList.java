package com.example.java_squad;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.java_squad.Geo.SelectLocationActivity;

import java.util.ArrayList;

public class BinomialCustomList extends ArrayAdapter<Binomial> {
    private ArrayList<Binomial> trials;
    private Context context;
    private ImageView addmap;

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
        addmap = view.findViewById(R.id.map);
        if (binomial.getEnableGeo() == 1){
            addmap.setImageResource(R.drawable.ic_baseline_map_red);
        }
        if(binomial.getEnableGeo() == 0){
            addmap.setImageResource(R.drawable.ic_baseline_map_white);
        }

        String result = binomial.getResult();
        String experimenterName = binomial.getExperimenter();

        resultView.setText(result);
        nameView.setText(experimenterName);

        return view;
    }
}

