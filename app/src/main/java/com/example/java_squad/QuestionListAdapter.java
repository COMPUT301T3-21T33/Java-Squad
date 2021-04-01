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

public class QuestionListAdapter extends ArrayAdapter {
    private Activity mContext;
    List<Question> questionList;

    public QuestionListAdapter( Activity mContext, List<Question> questionList){
        super(mContext, R.layout.questions_item, questionList);
        this.mContext = mContext;
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        View listView = inflater.inflate(R.layout.questions_item, null, true);

        TextView questionView = listView.findViewById(R.id.question_text);

        Question question = questionList.get(position);

        questionView.setText(question.getText());

        return listView;
    }
    
}
