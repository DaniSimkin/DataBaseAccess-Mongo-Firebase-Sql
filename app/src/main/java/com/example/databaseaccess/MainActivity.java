package com.example.databaseaccess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 4000;

    Animation topAnim, bottomAnim;
    ImageView dbImage;
    TextView logo, info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //To hide the status bar, action bar has already been hidden in styles.xml
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        dbImage = findViewById(R.id.imageViewDB);
        logo = findViewById(R.id.textViewLogo);
        info = findViewById(R.id.textViewInfo);

        dbImage.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        info.setAnimation(bottomAnim);

        //Simply handles how long the splash screen will appear before moving on to the new app screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,UponEntranceActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);


    }
}
