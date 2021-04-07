package com.example.java_squad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewQuestionActivity<FirebaseRecycleOption> extends AppCompatActivity {

    Button addQuestionBtn;
    TextView question;
    DatabaseReference df;
    RecyclerView recyclerView;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_question);
        intent = getIntent();
        recyclerView = findViewById(R.id.question_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        df = FirebaseDatabase.getInstance().getReference("Question")
                .child(intent.getStringExtra("experimentName"));


        question = findViewById(R.id.add_question);
        addQuestionBtn = findViewById(R.id.add_question_button);

        //add question to database
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


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Question> options =
                new FirebaseRecyclerOptions.Builder<Question>()
                        .setQuery(df, Question.class)
                        .build();

        FirebaseRecyclerAdapter<Question, myViewHolder> adapter =
                new FirebaseRecyclerAdapter<Question, myViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Question model) {
                        holder.questionView.setText(model.getText());
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String visit = getRef(position).getKey();
                                String experimentName = intent.getStringExtra("experimentName");
                                Intent intent = new Intent(v.getContext(), ViewReplyActivity.class);
                                intent.putExtra("questionID", visit);
                                intent.putExtra("experimentName", experimentName);
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.questions_item, parent, false);
                        return new myViewHolder(view);
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        TextView questionView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            questionView = itemView.findViewById(R.id.question_text);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_icon);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Question");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                Searching(newText);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Searching(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void Searching(String s) {

        FirebaseRecyclerOptions<Question> options =
                new FirebaseRecyclerOptions.Builder<Question>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Question")
                                .child(intent.getStringExtra("experimentName")).orderByChild("text").startAt(s)
                                .endAt(s + "\uf8ff"), Question.class)
                        .build();

        FirebaseRecyclerAdapter<Question, myViewHolder> adapter1 =
                new FirebaseRecyclerAdapter<Question, myViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Question model) {
                        holder.questionView.setText(model.getText());
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String visit = getRef(position).getKey();
                                String experimentName = intent.getStringExtra("experimentName");
                                Intent intent = new Intent(v.getContext(), ViewReplyActivity.class);
                                intent.putExtra("questionID", visit);
                                intent.putExtra("experimentName", experimentName);
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.questions_item, parent, false);
                        return new myViewHolder(view);
                    }
                };

        adapter1.startListening();
        recyclerView.setAdapter(adapter1);
    }
}
