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

public class AddMeasurementTrialFragment extends DialogFragment {
    private EditText amount;
    private EditText unit;
    private EditText experimenter;
    private EditText date;
    private Button addMarker;
    private OnFragmentInteractionListener listener;
    private TextView warning;

    static AddMeasurementTrialFragment newInstance(Measurement measurement){
        Bundle args = new Bundle();
        args.putSerializable("measurement", measurement);

        AddMeasurementTrialFragment fragment = new AddMeasurementTrialFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnFragmentInteractionListener{
        void onOkPressed(Measurement newTrail);
//        void onOkPressedEdit(Measurement city, Measurement newcity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener) context;
        }else {
            throw new RuntimeException(context.toString()+" must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.measurement_trial_fragment,null);

        String geo = getArguments().getString("enable geo");
        Log.d("fragment get geo","get received = "+geo);

        if (geo.equals("1")){
            warning = (TextView) view.findViewById(R.id.warning);
            warning.setText("Geo-location is required");
        }
        amount= view.findViewById(R.id.amount);
        unit = view.findViewById(R.id.unit);
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

                        String set_amount = amount.getText().toString();
                        String set_unit = unit.getText().toString();
                        String set_experimenter = experimenter.getText().toString();
                        String dateString = date.getText().toString();
                        double amountDouble = Double.parseDouble(set_amount);
                        try {
                            Date dateDate = dateConverter.stringToDate(dateString);
                            Log.d("add experiment fragment","listener on ok pressed");
                            listener.onOkPressed(new Measurement(set_experimenter,dateDate,0,1000.0,1000.0,set_unit,amountDouble));
                        } catch (ParseException e) {
                            String msg = "Pleas enter a date in yyyy-mm-dd format";
                            Toast.makeText(getActivity().getBaseContext(), msg, Toast.LENGTH_LONG).show();
                        }

                    }
                }).create();
        // getArguments().getSerializable("someInt", 0);
    }
}
