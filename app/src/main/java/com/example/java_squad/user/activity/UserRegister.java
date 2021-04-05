package com.example.java_squad.user.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.java_squad.R;
import com.example.java_squad.user.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserRegister extends AppCompatActivity {
    private EditText createUsername;
    private EditText createID;
    private Button registerButton;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_register);
//
//        createUsername = findViewById(R.id.EditText_username);
//        createID = findViewById(R.id.EditText_contact);
//        registerButton = findViewById(R.id.Button_register);
//    }
//
//    public void registerButtonOnClick(View v){
//        String username = createUsername.getText().toString();
//        String ID = createID.getText().toString();
//
//        if(username.equals("") || ID.equals("")){
//            new AlertDialog.Builder(UserRegister.this)
//                    .setTitle("Blank")
//                    .setMessage("Please Provide username and contact info")
//                    .setPositiveButton("OK", null)
//                    .show();
//            return;
//        }
//
//        User user = new User(username, ID);
//        FirebaseFirestore.getInstance().collection("Users")
//                .document(username)
//                .set(user)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        // return our user object through an intent
//                        Intent intent = new Intent();
//                        intent.putExtra("UserRegister.user", user);
//                        setResult(Activity.RESULT_OK, intent);
//                        finish();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) { Log.d("UserRegister.DB", "Failed to add the new user to db. Detail: ", e); }
//                });
//    }

}
