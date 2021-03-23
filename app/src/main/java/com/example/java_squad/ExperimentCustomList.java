package com.example.java_squad;

import android.content.Context;
import android.media.ExifInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ExperimentCustomList extends ArrayAdapter<Experimental> {
    private ArrayList<Experimental> experiments;
    private Context context;

    public ExperimentCustomList(Context context, ArrayList<Experimental> experiments){
        super(context,0, experiments);
        this.experiments = experiments;
        this.context = context;
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.followed_exp_content,parent,false);
        }

        Experimental experiment = experiments.get(position);
        /**
         * This section will need to be changed to show the results of different kinds of trials
         */
        TextView experiment_name = view.findViewById(R.id.experiment);
        TextView type = view.findViewById(R.id.type);
        Integer exp_type = experiment.getType();
        experiment_name.setText(experiment.getName());
        type.setText(String.valueOf(exp_type));

        return view;
    }
}
