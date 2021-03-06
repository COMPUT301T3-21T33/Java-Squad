package com.example.java_squad;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.java_squad.Geo.SelectLocationActivity;
import com.example.java_squad.Geo.SelectLocationFragment;

import java.text.ParseException;
import java.util.Date;
/**
 * AddBinomialTrialFragment class providing a dialog for user to add a trial for binomial type experiment
 */
public class AddBinomialTrialFragment extends DialogFragment {
    //https://www.youtube.com/watch?v=fwSJ1OkK304
    private TextView result;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private EditText experimenter;

    private OnFragmentInteractionListener listener;
    private TextView warning;


    static AddBinomialTrialFragment newInstance(Binomial binomial){
        Bundle args = new Bundle();
        args.putSerializable("binomial", binomial);

        AddBinomialTrialFragment fragment = new AddBinomialTrialFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnFragmentInteractionListener{
        void onOkPressed(Binomial newTrail);
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
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.binomial_trial_fragment,null);

        String geo = getArguments().getString("enable geo");
        Log.d("fragment get geo","get received = "+geo);

        if (geo.equals("1")){
            warning = (TextView) view.findViewById(R.id.warning);
            warning.setText("Geo-location is required");
        }

        result= view.findViewById(R.id.result);
        experimenter= view.findViewById(R.id.author);

        radioGroup = view.findViewById(R.id.radioGroup);
//       final Bundle arg = getArguments();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("Add trial")
                .setNegativeButton("Cancel",null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String set_experimenter = experimenter.getText().toString();
                        int radioId = radioGroup.getCheckedRadioButtonId();
                        radioButton = view.findViewById(radioId);
                        String set_result = radioButton.getText().toString();
                        listener.onOkPressed(new Binomial(set_experimenter,"",0,1000.0,1000.0,set_result));

                    }
                }).create();
        // getArguments().getSerializable("someInt", 0);
    }
}

