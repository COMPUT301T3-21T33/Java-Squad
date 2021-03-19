package com.test.serachapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.java_squad.R;

public class MainActivity2 extends AppCompatActivity {

    private EditText etName;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName = findViewById(R.id.et_name);
        etPassword = findViewById(R.id.et_password);
        findViewById(R.id.bt_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }


        });
    }
    private void login() {
        String name = etName.getText().toString();
        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(name)){
            return;
        }
        if (TextUtils.isEmpty(password)){
            return;
        }
        if (name.equals("123456")&&password.equals("123456")){
            startActivity(new Intent(this, com.test.serachapp.SearchActivity.class));
        }else{
            Toast.makeText(this,"error",Toast.LENGTH_SHORT).show();
        }
    }
}
