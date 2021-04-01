package com.example.java_squad;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.ParseException;
import java.util.Date;

public class AddIntCountTrialFragment extends DialogFragment {
    private Button increase;
    private Button decrease;
    private TextView count;
    private EditText experimenter;
    private EditText date;
    private OnFragmentInteractionListener listener;


    static AddIntCountTrialFragment newInstance(IntCount intCount){
        Bundle args = new Bundle();
        args.putSerializable("intCount", intCount);

        AddIntCountTrialFragment fragment = new AddIntCountTrialFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnFragmentInteractionListener{
        void onOkPressed(IntCount newTrail);
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
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.int_count_trial_fragment,null);
        count= view.findViewById(R.id.count);
        experimenter= view.findViewById(R.id.author);
        date= view.findViewById(R.id.date);

        increase = view.findViewById(R.id.increase);
        decrease = view.findViewById(R.id.decrease);

        // set onclicklistener
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentCountInString = count.getText().toString();
                Integer currentCount = Integer.parseInt(currentCountInString);
                currentCount = currentCount +1;
                String countInString = String.valueOf(currentCount);
                count.setText(countInString);
            }
        });

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentCountInString = count.getText().toString();
                Integer currentCount = Integer.parseInt(currentCountInString);
                currentCount = currentCount - 1;
                if (currentCount >= 0){
                    String countInString = String.valueOf(currentCount);
                    count.setText(countInString);
                }
            }
        });
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

                        String set_count = count.getText().toString();
                        String set_experimenter = experimenter.getText().toString();
                        String dateString = date.getText().toString();
                        Integer amountInt = Integer.parseInt(set_count);
                        try {
                            Date dateDate = dateConverter.stringToDate(dateString);
                            Log.d("add experiment fragment","listener on ok pressed");
                            listener.onOkPressed(new IntCount(set_experimenter,dateDate,amountInt));
                        } catch (ParseException e) {
                            String msg = "Pleas enter a date in yyyy-mm-dd format";
                            Toast.makeText(getActivity().getBaseContext(), msg, Toast.LENGTH_LONG).show();
                        }

                    }
                }).create();
        // getArguments().getSerializable("someInt", 0);
    }
}
