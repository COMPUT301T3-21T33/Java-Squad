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

    public ReplyAdapter( Activity mContext, List<String> questionList){
        super(mContext, R.layout.questions_item, questionList);
        this.mContext = mContext;
        this.replyList = questionList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        View listView = inflater.inflate(R.layout.reply_item, null, true);

        TextView questionView = listView.findViewById(R.id.reply_text);

        String answer = replyList.get(position);

        questionView.setText(answer);

        return listView;
    }

}

