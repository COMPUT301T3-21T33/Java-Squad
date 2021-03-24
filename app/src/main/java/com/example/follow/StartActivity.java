package com.example.follow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.google.firebase.firestore.FirebaseFirestore;

public class StartActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentuser;
    FirebaseDatabase db;
    DatabaseReference df;
    AuthCredential ac;
    Button editp, search;
    TextView id;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mAuth = FirebaseAuth.getInstance();
        currentuser = mAuth.getCurrentUser();

        editp = findViewById(R.id.editprofile);
        id = findViewById(R.id.id);
        db = FirebaseDatabase.getInstance();
        df = db.getReference("Users");
        search = findViewById(R.id.search);

       Toast.makeText(StartActivity.this, "hello", Toast.LENGTH_LONG).show();
        if (currentuser == null) {
            Toast.makeText(StartActivity.this, "creating", Toast.LENGTH_LONG).show();
            mAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        id.setText(currentuser.getUid());
                        Toast.makeText(StartActivity.this, "successfully created account", Toast.LENGTH_LONG).show();


                    } else {
                        Toast.makeText(StartActivity.this, "fail to created account", Toast.LENGTH_LONG).show();
                    }
                }

            });
        } else {
            Toast.makeText(StartActivity.this, "Loged in", Toast.LENGTH_LONG).show();
            id.setText(currentuser.getUid());

        }




       if (currentuser.isAnonymous()) {
            Toast.makeText(StartActivity.this, "isAnonymous", Toast.LENGTH_LONG).show();
            ac = EmailAuthProvider.getCredential("www@www.com", "123456");
            currentuser.linkWithCredential(ac).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(StartActivity.this, "successfully created account", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(StartActivity.this, "fail to update create account", Toast.LENGTH_LONG).show();

                    }
                }
            });
        }



        editp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(v.getContext(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

       search.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(v.getContext(), ViewFollow.class);
               startActivity(intent);

           }
       });


    }
}

/*
  @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }

    private void updateUI(FirebaseUser currentUser) {

        if (currentUser == null) {

            mAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser cuser = mAuth.getCurrentUser();
                        updateUI(cuser);

                        Toast.makeText(StartActivity.this, "successfully created account", Toast.LENGTH_LONG).show();

                    }
                }

            });

        }
        else{
            updateUI(null);
        }
    }}
*/
