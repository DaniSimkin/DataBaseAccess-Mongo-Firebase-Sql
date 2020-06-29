package com.example.databaseaccess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserScreenActivity extends AppCompatActivity {

    TextInputLayout fullName, email, phoneNo, password;
    TextView fullNameNextToIcon, userNameUnderFullName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_screen);

        fullName = findViewById(R.id.fullNameTxt);
        email = findViewById(R.id.emailTxt);
        phoneNo = findViewById(R.id.phoneNumTxt);
        password = findViewById(R.id.pwTxt);
        fullNameNextToIcon = findViewById(R.id.user_profile_name);
        userNameUnderFullName = findViewById(R.id.nameLabel);


        showAllUserData();

        
    }

    private void showAllUserData() {

        Intent intent = getIntent();
        String user_username = intent.getStringExtra("username");
        String user_name = intent.getStringExtra("name");
        String user_email = intent.getStringExtra("email");
        String user_phoneNo = intent.getStringExtra("phone");
        String user_password = intent.getStringExtra("password");

        fullNameNextToIcon.setText(user_name);
        userNameUnderFullName.setText(user_username);
        fullName.getEditText().setText(user_name);
        email.getEditText().setText(user_email);
        phoneNo.getEditText().setText(user_phoneNo);
        password.getEditText().setText(user_password);
    }


}
