package com.example.java_squad;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.ParseException;
import java.util.Date;

public class AddCountTrialFragment extends DialogFragment {
    private EditText object;
    private EditText count;
    private EditText experimenter;
    private EditText date;
    private Button addMarker;
    private AddCountTrialFragment.OnFragmentInteractionListener listener;
    private TextView warning;

    static AddCountTrialFragment newInstance(Count count){
        Bundle args = new Bundle();
        args.putSerializable("count", count);

        AddCountTrialFragment fragment = new AddCountTrialFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnFragmentInteractionListener{
        void onOkPressed(Count newTrail);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddCountTrialFragment.OnFragmentInteractionListener){
            listener = (AddCountTrialFragment.OnFragmentInteractionListener) context;
        }else {
            throw new RuntimeException(context.toString()+" must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.count_trial_fragment,null);

        String geo = getArguments().getString("enable geo");
        Log.d("fragment get geo","get received = "+geo);

        if (geo.equals("1")){
            warning = (TextView) view.findViewById(R.id.warning);
            warning.setText("Geo-location is required");
        }

        object= view.findViewById(R.id.object);
        count = view.findViewById(R.id.count);
        experimenter= view.findViewById(R.id.author);
        date= view.findViewById(R.id.date);
//        final Bundle arg = getArguments();
        DateConverter dateConverter = new DateConverter();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("Add trial")
                .setNegativeButton("Cancel",null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String set_object = object.getText().toString();
                        String set_amount = count.getText().toString();
                        String set_experimenter = experimenter.getText().toString();
                        Integer amount = Integer.parseInt(set_amount);
                        listener.onOkPressed(new Count(set_experimenter,"",0,1000.0,1000.0,set_object,amount));

                    }
                }).create();
    }
}
