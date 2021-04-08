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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {
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
        name = findViewById(R.id.username);
        email = findViewById(R.id.useremail);
        update = findViewById(R.id.updateprofile);
        String userid = intent.getStringExtra("id");
        df = FirebaseDatabase.getInstance().getReference("User");


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = name.getText().toString();
                String useremail = email.getText().toString();


                HashMap data = new HashMap();
                data.put("username", username);
                data.put("contact", useremail);

                Query query = df.orderByChild("username").equalTo(username);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Boolean isUsername = false;

                        for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                            if (dataSnapshot.child("username").getValue().toString().equals(username))
                            {
                                isUsername = true;
                            }
                        }

                        if(username.equals("") || useremail.equals("")) {
                            if (username.equals("")){name.setError("Name cannot be empty");}
                            else{email.setError("Email cannot be empty");}
                        }
                        else if (isUsername){
                            name.setError("This name have been used");
                        }
                        else{
                            df.child(userid).updateChildren(data);
                            name.setText("");
                            email.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });


    }
}
