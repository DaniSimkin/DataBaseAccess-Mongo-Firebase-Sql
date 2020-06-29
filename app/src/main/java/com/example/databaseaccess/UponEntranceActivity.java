package com.example.databaseaccess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;


import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.Format;

public class UponEntranceActivity extends AppCompatActivity {

    Button callSignUpBtn, loginBtn;
    ImageView dbImageTransparent, change_dbs;
    TextView greetingTxt, underGreetingTxt;
    TextInputLayout userName, password;
    ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //To hide the status bar, action bar has already been hidden in styles.xml
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_upon_entrance);

        loginBtn = findViewById(R.id.goBtn);
        callSignUpBtn = findViewById(R.id.signUpBtn);
        dbImageTransparent = findViewById(R.id.transparentLogo);
        greetingTxt = findViewById(R.id.transparentLogoName);
        underGreetingTxt = findViewById(R.id.infoName);
        userName = findViewById(R.id.usernameText);
        password = findViewById(R.id.passwordText);
        pb = findViewById(R.id.pBar);
        pb.setVisibility(View.INVISIBLE);
        change_dbs = findViewById(R.id.db_options);


    }

    public void callSignUpScreen(View view) {
        Intent intent = new Intent(UponEntranceActivity.this, SignUpActivity.class);

        Pair[] pairs = new Pair[3];

        pairs[0] = new Pair<View, String>(dbImageTransparent, "db_logo");
        pairs[1] = new Pair<View, String>(greetingTxt, "greeting_text");
        pairs[2] = new Pair<View, String>(underGreetingTxt, "under_greeting_text");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(UponEntranceActivity.this, pairs);
        startActivity(intent, options.toBundle());
    }

    private Boolean validateUserName() {
        String val = userName.getEditText().getText().toString();
        if (val.isEmpty()) {
            userName.setError("Field cannot be empty");
            return false;
        } else {
            userName.setError(null);
            userName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();
        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public void loginUser(View view) {
        //Validate Login Info
        if (!validateUserName() | !validatePassword()) {
            return;
        } else {
            isUser();
        }
    }

    private void isUser() {
        pb.setVisibility(View.VISIBLE);
        final String userEnteredUsername = userName.getEditText().getText().toString().trim();
        final String userEnteredPassword = password.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    userName.setError(null);
                    userName.setErrorEnabled(false);

                    String passwordFromDB = dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);

                    if (passwordFromDB.equals(userEnteredPassword)) {

                        userName.setError(null);
                        userName.setErrorEnabled(false);

                        String nameFromDB = dataSnapshot.child(userEnteredUsername).child("name").getValue(String.class);
                        String usernameFromDB = dataSnapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        String phoneNoFromDB = dataSnapshot.child(userEnteredUsername).child("phone").getValue(String.class);
                        String emailFromDB = dataSnapshot.child(userEnteredUsername).child("email").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), UserScreenActivity.class);

                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("phone", phoneNoFromDB);
                        intent.putExtra("password", passwordFromDB);

                        startActivity(intent);
                        pb.setVisibility(View.GONE);
                    } else {
                        pb.setVisibility(View.GONE);
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                } else {
                    pb.setVisibility(View.GONE);
                    userName.setError("No such User exists");
                    userName.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void changeDatabase(View view) {

        PopupMenu otherDb = new PopupMenu(this, change_dbs);
        otherDb.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getTitle().toString()) {
                    case "SQLite DataBase":
                        Intent SqlIntent = new Intent(UponEntranceActivity.this, SQLActivity.class);
                        startActivity(SqlIntent);
                        return true;

                    case "MongoDB":
                        Intent mongoIntent = new Intent(UponEntranceActivity.this, MongoActivity.class);
                         startActivity(mongoIntent);
                        return true;

                }
                return false;
            }
        });

        otherDb.inflate(R.menu.menu_main);

        try {


            Field[] fields = otherDb.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(otherDb);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;



                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        finally {
            otherDb.show();
        }
    }
}
