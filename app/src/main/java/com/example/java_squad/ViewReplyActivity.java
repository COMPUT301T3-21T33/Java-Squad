package com.example.java_squad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewReplyActivity extends AppCompatActivity {
    TextView questionView;
    DatabaseReference df;
    ListView replyListView;
    Intent intent;
    List<String> replyList;
    ReplyAdapter adapter;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reply);
        questionView = findViewById(R.id.question_text_view);
        replyListView = findViewById(R.id.reply_list);
        replyList = new ArrayList<>();

        intent = getIntent();
        String questionID = intent.getStringExtra("questionID");

        df = FirebaseDatabase.getInstance().getReference("Question")
                .child(intent.getStringExtra("experimentName")).child(questionID);
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String text = snapshot.child("text").getValue().toString();
                questionView.setText(text);
                //Log.d("frieda4", snapshot.child("reply").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Log.d("frieda", questionID);

        DatabaseReference dataref = FirebaseDatabase.getInstance().getReference("Question")
                .child(intent.getStringExtra("experimentName")).child(questionID);

        dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                Log.d("frieda1", String.valueOf(datasnapshot.hasChild("reply")));
                if (datasnapshot.hasChild("reply")) {
                    replyList.clear();
                    for (DataSnapshot snapshot : datasnapshot.child("reply").getChildren()) {
                        String index = snapshot.getValue(String.class);
                        replyList.add(index);
                    }
                    adapter =
                            new ReplyAdapter(ViewReplyActivity.this, replyList);
                    replyListView.setAdapter(adapter);
                }
                else{
                    replyList.clear();
                    adapter =
                            new ReplyAdapter(ViewReplyActivity.this, replyList);
                    replyListView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        questionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editView = new EditText(ViewReplyActivity.this);
                new AlertDialog.Builder(ViewReplyActivity.this)
                        .setTitle("Add reply")
                        .setView(editView)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String answer = editView.getText().toString();
                                df.child("reply").push().setValue(answer);
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
            }
        });
        back= findViewById(R.id.back_to_question);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


}