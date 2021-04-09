package com.example.java_squad;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ReplyAdapter extends ArrayAdapter {
    private Activity mContext;
    List<String> replyList;
    //creat replyadapter constructor
    public ReplyAdapter( Activity mContext, List<String> questionList){
        super(mContext, R.layout.questions_item, questionList);
        this.mContext = mContext;
        this.replyList = questionList;
    }
    //the view on the screen
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        //get content for each reply
        View listView = inflater.inflate(R.layout.reply_item, null, true);
        //get texte view for reply
        TextView questionView = listView.findViewById(R.id.reply_text);
        //get reply from replyactivity class
        String answer = replyList.get(position);
        //set reply and show on screen
        questionView.setText(answer);

        return listView;
    }

}

