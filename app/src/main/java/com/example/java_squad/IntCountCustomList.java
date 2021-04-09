package com.example.java_squad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
/**
 * IntCountCustomList class is used for array adapter to show a int count trial in the ListView.
 */
public class IntCountCustomList extends ArrayAdapter<IntCount> {
    private ArrayList<IntCount> trials;
    private Context context;

    public IntCountCustomList(Context context, ArrayList<IntCount> trials){
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
        IntCount count = trials.get(position);
        TextView amount = view.findViewById(R.id.count);
        TextView name = view.findViewById(R.id.experimenter);
        ImageView addmap = view.findViewById(R.id.map);
        if (count.getEnableGeo() == 1){
            addmap.setImageResource(R.drawable.ic_baseline_map_red);
        }
        if(count.getEnableGeo() == 0){
            addmap.setImageResource(R.drawable.ic_baseline_map_white);
        }
        Integer getAmount = count.getCount();
        String amountInString = Integer.toString(getAmount);
        String experimenterName = count.getExperimenter();

        amount.setText(amountInString);
        name.setText(experimenterName);

        return view;
    }
}
