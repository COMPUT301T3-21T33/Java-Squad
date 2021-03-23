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
 * Custom list class for displaying all experiments.
 * List entry currently displays Title, Owner's name, Type of trials being recorded, status (is active), and number of trials recorded so far.
 * Written by Michael Harbidge
 */
public class ExperimentCustomList extends ArrayAdapter<Experimental> {

    private ArrayList<Experimental> experiments;
    private Context context;

    public ExperimentCustomList(Context context, ArrayList<Experimental> experiments) {
        super(context,0,experiments);
        this.experiments = experiments;
        this.context = context;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.experiment_list_content,parent,false);
        }

        Experimental experiment = experiments.get(position);

        TextView expTitle = view.findViewById(R.id.exp_list_title);
        TextView expOwner = view.findViewById(R.id.exp_list_owner);
        TextView expType = view.findViewById(R.id.exp_list_type);
        TextView expActive = view.findViewById(R.id.exp_list_active);
        TextView expTrials = view.findViewById(R.id.exp_list_trials);

        expTitle.setText(experiment.getName()); //Should be changed for consistency

        String ownerString = "Owner: " + experiment.getOwnerName();
        expOwner.setText(ownerString);

        String typeString = "Type: " + experiment.getTypeString();
        expType.setText(typeString);

        if (experiment.getActive())
            expActive.setText("In Progress");
        else
            expActive.setText("Finished");

        expTrials.setText(String.valueOf(experiment.trials.size()));

        return view;
    }
}
