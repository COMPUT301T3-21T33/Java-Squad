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
    private AddCountTrialFragment.OnFragmentInteractionListener listener;


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
                        String dateString = date.getText().toString();
                        Integer amountDouble = Integer.parseInt(set_amount);
                        try {
                            Date dateDate = dateConverter.stringToDate(dateString);
                            Log.d("add experiment fragment","listener on ok pressed");
                            listener.onOkPressed(new Count(set_experimenter,dateDate,set_object,amountDouble));
                        } catch (ParseException e) {
                            String msg = "Pleas enter a date in yyyy-mm-dd format";
                            Toast.makeText(getActivity().getBaseContext(), msg, Toast.LENGTH_LONG).show();
                        }

                    }
                }).create();
    }
}