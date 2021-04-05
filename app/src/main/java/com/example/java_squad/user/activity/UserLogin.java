package com.example.java_squad.user.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.java_squad.R;
import com.example.java_squad.user.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserLogin extends AppCompatActivity {
    private final int userRegister = 1;
    private EditText createUsername;
    private Button buttonLogin;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        createUsername = findViewById(R.id.EditText_username);
        buttonLogin = findViewById(R.id.Button_login);
        buttonRegister = findViewById(R.id.Button_register);

    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case userRegister: {
//                if (resultCode == Activity.RESULT_OK) {
//                    User user = (User) data.getSerializableExtra("UserRegister.user");
//                    createUsername.setText(user.getUsername());
//                }
//            }
//        }
//    }
//
//    public void btLoginOnClick(View v) {
//        String username = createUsername.getText().toString();
//
//        if (username.equals("")) {
//            new AlertDialog.Builder(UserLogin.this)
//                    .setTitle("Empty Fields")
//                    .setMessage("Please provide username.")
//                    .setPositiveButton("OK", null)
//                    .show();
//            return;
//        }
//
//
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        CollectionReference users = db.collection("Users");
//
//        users.document(username)
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if (!documentSnapshot.exists()) {                   // query returned no document!
//                            new AlertDialog.Builder(UserLogin.this)
//                                    .setTitle("Invalid Username")
//                                    .setMessage("This username is not a registered user. Please register your username.")
//                                    .setPositiveButton("OK", null)
//                                    .show();
//                            return;
//                        }
//
//                        User user = documentSnapshot.toObject(User.class);
//                        Intent intent = new Intent();
//                        intent.putExtra("UserLogin.user", user);
//                        setResult(0, intent);
//                        finish();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d("UserLogin.DB", "Failed to perform query. Detail: ", e);
//                    }
//                });
//    }
//
//    public void btRegisterOnClick(View v) {
//        Intent intent = new Intent(this, UserRegister.class);
//        startActivityForResult(intent, userRegister);
//    }
}
