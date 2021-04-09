package com.example.java_squad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
/**
 * ShowAllOwnedActivity class showing all the experiments owned by the user
 */
public class ShowAllOwnedActivity extends AppCompatActivity {
    ListView showAllOwnedList;
    ArrayAdapter<Experimental> allExpAdapter; // Bridge between dataList and cityList.
    ArrayList<Experimental> allExpDataList; // Holds the data that will go into the listview
    DatabaseReference df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_owned);
        showAllOwnedList = findViewById(R.id.show_all_owned_list);
        Intent intent = getIntent();
        String userid = intent.getStringExtra("id");

        allExpDataList = new ArrayList<>();
        df = FirebaseDatabase.getInstance().getReference("User").child(userid);
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("Experiment")){
                    allExpDataList.clear();
                    for (DataSnapshot datasnapshot: snapshot.child("Experiment").getChildren()){
                        Experimental exp = datasnapshot.getValue(Experimental.class);
                        allExpDataList.add(exp);
                    }
                    allExpAdapter.notifyDataSetChanged();
                }
                else{
                    allExpDataList.clear();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        allExpAdapter = new ExperimentCustomList(this, allExpDataList);
        showAllOwnedList.setAdapter(allExpAdapter);



        showAllOwnedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d("main activity","On item click to record trials");
                Experimental experiment = (Experimental) adapterView.getAdapter().getItem(i);//experiments[i];



                /*Intent intent = new Intent(ShowAllOwnedActivity.this, ExperimentView.class);

                intent.putExtra("experiment", experiment);
                intent.putExtra("id", userid);
                startActivity(intent);*/

                Integer exp_type = experiment.getType();
                if (exp_type == 0){
                    Intent intent = new Intent(ShowAllOwnedActivity.this, ExperimentViewIntCount.class);

                    //Log.d("main activity","on item click to start record trails");

                    intent.putExtra("experiment", experiment);
                    intent.putExtra("id", userid);
                    startActivity(intent);

                }
                else if (exp_type == 1) {
                    Intent intent = new Intent(ShowAllOwnedActivity.this, ExperimentViewBinomial.class);

                    //Log.d("main activity","on item click to start record trails");

                    intent.putExtra("experiment", experiment);
                    intent.putExtra("id", userid);
                    startActivity(intent);
                }
                else if (exp_type == 2) {
                    Intent intent = new Intent(ShowAllOwnedActivity.this, ExperimentViewCount.class);

                    //Log.d("main activity","on item click to start record trails");

                    intent.putExtra("experiment", experiment);
                    intent.putExtra("id", userid);
                    startActivity(intent);
                }
                else if (exp_type == 3) {
                    Intent intent = new Intent(ShowAllOwnedActivity.this, ExperimentViewMeasurement.class);

                    //Log.d("main activity","on item click to start record trails");

                    intent.putExtra("experiment", experiment);
                    intent.putExtra("id", userid);
                    startActivity(intent);
                }



            }
        });

    }
}