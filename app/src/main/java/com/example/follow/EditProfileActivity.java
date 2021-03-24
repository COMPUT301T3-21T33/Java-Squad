package com.example.follow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class EditProfileActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser current;
    EditText name, email;
    Button update;
    FirebaseDatabase db;
    DatabaseReference df;
    String username;
    String useremail;
    AuthCredential ac;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mAuth = FirebaseAuth.getInstance();
        current = mAuth.getCurrentUser();
        name = findViewById(R.id.username);
        email = findViewById(R.id.useremail);
        update = findViewById(R.id.updateprofile);
        username = name.getText().toString();
        useremail = email.getText().toString();
        df = FirebaseDatabase.getInstance().getReference("User").child(current.getUid()).child("profile");
        Log.d("find1", "before clicked update");

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    user = new User();
                    user.setEmail(useremail);
                    user.setUsername(username);
                    user.setFollowedExperiment(null);

                    df.push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {

                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Log.d("find2", "clicked update");
                                Toast.makeText(EditProfileActivity.this, "successfully created account", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Log.d("find3", "clicked update");
                                Toast.makeText(EditProfileActivity.this, "fail to update account", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }

        });


    }
}