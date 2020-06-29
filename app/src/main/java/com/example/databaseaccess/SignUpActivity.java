package com.example.databaseaccess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    TextInputLayout name, userName, email, phoneNo, password;
    Button regBtn, backToLoginBtn;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //To hide the status bar, action bar has already been hidden in styles.xml
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.name);
        userName = findViewById(R.id.username);
        email = findViewById(R.id.email);
        phoneNo = findViewById(R.id.phoneNumber);
        password = findViewById(R.id.password);
        regBtn = findViewById(R.id.reg_btn);
        backToLoginBtn = findViewById(R.id.reg_back_to_login_btn);

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference().child("users");

    }

    private Boolean validateName() {
        String val = name.getEditText().getText().toString();

        if (val.isEmpty()) {
            name.setError("Field cannot be empty");
            return false;
        } else {
            name.setError(null);
            name.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUserName() {
        String val = userName.getEditText().getText().toString();
        String noWhiteSpaces = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            userName.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            userName.setError("Username is too long");
            return false;
        } else if (!val.matches(noWhiteSpaces)) {
            userName.setError("White spaces not allowed");
            return false;
        } else {
            userName.setError(null);
            userName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = email.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            name.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            email.setError("Invalid email address");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNumber() {
        String val = phoneNo.getEditText().getText().toString();

        if (val.isEmpty()) {
            phoneNo.setError("Field cannot be empty");
            return false;
        } else {
            phoneNo.setError(null);
            phoneNo.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            password.setError("Password is too weak");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }


    //Save the registered user to FireBase
    public void registerUser(View view) {

        if(!validateName() | !validateUserName() | !validateEmail() | !validatePhoneNumber() | !validatePassword()){
            return;
        }

        String sName = name.getEditText().getText().toString();
        String sUserName = userName.getEditText().getText().toString();
        String sEmail = email.getEditText().getText().toString();
        String sPhoneNo = phoneNo.getEditText().getText().toString();
        String sPassword = password.getEditText().getText().toString();
        User user = new User(sName, sUserName, sEmail, sPhoneNo, sPassword);
        reference.child(sUserName).setValue(user);
        Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();


    }

    public void backToLogin(View view){
        Intent intent = new Intent(SignUpActivity.this,UponEntranceActivity.class);
        startActivity(intent);
    }
}

