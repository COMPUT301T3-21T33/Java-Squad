package com.example.java_squad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewQuestionActivity extends AppCompatActivity {
    ListView questionListView;
    EditText question;
    Button addQuestionBtn;
    private int countQuestion = 0;
    List<Question> questionList;
    DatabaseReference df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_question);
        questionListView = findViewById(R.id.question_list);
        questionList = new ArrayList<>();

        Intent intent = getIntent();

        df = FirebaseDatabase.getInstance().getReference("Question")
                .child(intent.getStringExtra("experimentName"));
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
               
                questionList.clear();
                for(DataSnapshot snapshot: datasnapshot.getChildren()){
                    Question index = snapshot.getValue(Question.class);
                    Log.d("frieda4", index.getText());

                   questionList.add(index);
                }
                QuestionListAdapter adapter =
                        new QuestionListAdapter(ViewQuestionActivity.this, questionList);

                questionListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        question = findViewById(R.id.add_question);
        addQuestionBtn = findViewById(R.id.add_question_button);

        addQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getQuestion = question.getText().toString();
               
                Question newQuestion = new Question(getQuestion);
                df.push().setValue(newQuestion);
                question.setText("");

            }
        });

    }
}
