package com.example.java_squad;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.ParseException;
import java.util.Date;

public class AddBinomialTrialFragment extends DialogFragment {
    //https://www.youtube.com/watch?v=fwSJ1OkK304
    private TextView result;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private EditText experimenter;
    private EditText date;
    private OnFragmentInteractionListener listener;


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
        result= view.findViewById(R.id.result);
        experimenter= view.findViewById(R.id.author);
        date= view.findViewById(R.id.date);
        radioGroup = view.findViewById(R.id.radioGroup);

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

                        String set_experimenter = experimenter.getText().toString();
                        String dateString = date.getText().toString();
                        int radioId = radioGroup.getCheckedRadioButtonId();
                        radioButton = view.findViewById(radioId);
                        String set_result = radioButton.getText().toString();

                        try {
                            Date dateDate = dateConverter.stringToDate(dateString);
                            Log.d("add experiment fragment","listener on ok pressed");
                            listener.onOkPressed(new Binomial(set_experimenter,dateDate,0,set_result));
                        } catch (ParseException e) {
                            String msg = "Pleas enter a date in yyyy-mm-dd format";
                            Toast.makeText(getActivity().getBaseContext(), msg, Toast.LENGTH_LONG).show();
                        }

                    }
                }).create();
        // getArguments().getSerializable("someInt", 0);
    }
//    public void checkButton(View v) {
//        int radioId = radioGroup.getCheckedRadioButtonId();
//        radioButton = v.findViewById(radioId);
//    }
}

