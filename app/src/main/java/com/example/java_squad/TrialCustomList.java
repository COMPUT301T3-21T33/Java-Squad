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

/**
 * Custom Adapter for displaying the list of an experiment's trials in experiment view.
 */
public class TrialCustomList extends ArrayAdapter<Trial> {

    private ArrayList<Trial> trials;
    private Context context;

    public TrialCustomList(Context context, ArrayList<Trial> trials) {
        super(context,0,trials);
        this.trials = trials;
        this.context = context;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.trial_list_content,parent,false);
        }

        Trial trial = trials.get(position);
        /**
         * This section will need to be changed to show the results of different kinds of trials
         */
        TextView experimenter = view.findViewById(R.id.trial_list_experimenter);
        TextView date = view.findViewById(R.id.trial_list_date);

        experimenter.setText(trial.getExperimenter());
        date.setText(trial.getExperiment_date().toString());

        return view;
    }
}