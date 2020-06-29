package com.example.databaseaccess;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.URI;

public class SQLActivity extends AppCompatActivity {

    private EditText imageNameEditText;
    private ImageView objectImage;

    private static final int PICK_IMAGE_REQUEST = 100;
    private Uri imageFilePath;

    private Bitmap imageToStore;
    SQLiteDataBaseHandler sqLiteDataBaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_sql);

        try {
            imageNameEditText = findViewById(R.id.imageName_ET);
            objectImage = findViewById(R.id.image);

            sqLiteDataBaseHandler = new SQLiteDataBaseHandler(this);

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    public void selectImage(View view) {

        try {
            Intent intent = new Intent();
            intent.setType("image/*");

            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                imageFilePath = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), imageFilePath);

                objectImage.setImageBitmap(imageToStore);

            }

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    public void storeImage(View view) {

        try {

            if (!imageNameEditText.getText().toString().isEmpty() && objectImage.getDrawable() != null && imageToStore != null) {
                sqLiteDataBaseHandler.storeImage(new Image(imageNameEditText.getText().toString(), imageToStore));
            } else {
                Toast.makeText(this, "Please enter an image name and an image", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    public void showAllImages(View view) {
        startActivity(new Intent(this, SQLDisplayImagesActivity.class));
    }

}
