package com.example.java_squad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.java_squad.user.User;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {
    //FirebaseAuth mAuth;
    //FirebaseUser current;
    EditText name, email;
    Button update;
    FirebaseDatabase db;
    DatabaseReference df;
    AuthCredential ac;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Intent intent = getIntent();
        //mAuth = FirebaseAuth.getInstance();
        //current = mAuth.getCurrentUser();
        name = findViewById(R.id.username);
        email = findViewById(R.id.useremail);
        update = findViewById(R.id.updateprofile);

        df = FirebaseDatabase.getInstance().getReference("User").child(intent.getStringExtra("id"));


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = name.getText().toString();
                String useremail = email.getText().toString();
                //current.updateEmail(useremail);

                    /*user = new User();
                    user.setEmail(useremail);
                    user.setUsername(username);*/

                HashMap data = new HashMap();
                data.put("Name", username);
                data.put("Email", useremail);
                df.updateChildren(data);

                name.setText("");
                email.setText("");

            }

        });


    }
}