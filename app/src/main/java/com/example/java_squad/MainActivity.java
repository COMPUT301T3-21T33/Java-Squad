package com.example.java_squad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.java_squad.user.activity.UserLogin;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void launchLogin (View view){
        Intent intent = new Intent(this, UserLogin.class);
        startActivity(intent);
    }

    public void launchSearch (View view){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void launchExpView (View view){
        Intent intent = new Intent(this, ExperimentView.class);
        startActivity(intent);
    }

    public void launchExpConstructor (View view){
        Intent intent = new Intent(this, ExperimentView.class);
        startActivity(intent);
    }
}
