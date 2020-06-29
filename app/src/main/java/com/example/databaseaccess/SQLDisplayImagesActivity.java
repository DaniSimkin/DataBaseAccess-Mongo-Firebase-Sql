package com.example.databaseaccess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SQLDisplayImagesActivity extends AppCompatActivity {

    private SQLiteDataBaseHandler sqLiteDataBaseHandler;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_q_l_display_images);

        try {

            recyclerView = findViewById(R.id.images_rv);
            sqLiteDataBaseHandler = new SQLiteDataBaseHandler(this);

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    public void getData(View view) {
        try {
            recyclerViewAdapter = new RecyclerViewAdapter(sqLiteDataBaseHandler.getAllImagesData());
            recyclerView.setHasFixedSize(true);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(recyclerViewAdapter);

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }
}
