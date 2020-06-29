package com.example.databaseaccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class SQLiteDataBaseHandler extends SQLiteOpenHelper {

    Context context;
    private static String DATABASE_NAME = "mydb.db";
    private static int DATABASE_VERSION = 1;
    private static String createTableQuery = "create table imageInfo (imageName TEXT" +
            ",image BLOB)";

    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] imageInBytes;

    public SQLiteDataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {

            db.execSQL(createTableQuery);
            Toast.makeText(context, "Table has been successfully created", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void storeImage(Image image) {

        try {

            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            Bitmap imageToStoreAsBitmap = image.getImage();

            byteArrayOutputStream = new ByteArrayOutputStream();
            imageToStoreAsBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

            imageInBytes = byteArrayOutputStream.toByteArray();
            ContentValues contentValues = new ContentValues();

            contentValues.put("imageName", image.getImageName());
            contentValues.put("image", imageInBytes);

            long checkIfQueryRuns = sqLiteDatabase.insert("imageInfo", null, contentValues);
            if (checkIfQueryRuns != -1) {
                Toast.makeText(context, "Data added successfully into table", Toast.LENGTH_SHORT).show();
                sqLiteDatabase.close();
            } else {
                Toast.makeText(context, "Failed to add data into table", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    public ArrayList<Image> getAllImagesData() {

        try {

            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase(); // WAS writible - WHICH WAS wrong i think in my head
            ArrayList<Image> imageArrayList = new ArrayList<>();

            Cursor cursor = sqLiteDatabase.rawQuery("select * from imageInfo", null);
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    String nameOfImage = cursor.getString(0);
                    byte [] byteOfImage = cursor.getBlob(1);

                    Bitmap bitmap = BitmapFactory.decodeByteArray(byteOfImage, 0, byteOfImage.length);
                    imageArrayList.add(new Image(nameOfImage, bitmap));
                }
                return imageArrayList;

            } else {
                Toast.makeText(context, "There Are No Images In The DataBase", Toast.LENGTH_SHORT).show();
                return null;
            }

        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }


}
