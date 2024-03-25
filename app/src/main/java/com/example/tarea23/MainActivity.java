package com.example.tarea23;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ADD_PHOTO = 1;

    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private ListView listView;
    private List<Photograph> photographs;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        listView = findViewById(R.id.listView);
        photographs = new ArrayList<>();

        retrieveData();

        adapter = new CustomAdapter(this, photographs);
        listView.setAdapter(adapter);

        Button buttonAddPhoto = findViewById(R.id.btnAddPhoto);
        buttonAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPhoto();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

    private void retrieveData() {
        String[] projection = {DBHelper.COLUMN_IMAGE, DBHelper.COLUMN_DESCRIPTION};
        Cursor cursor = database.query(DBHelper.TABLE_NAME, projection, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") byte[] imageBytes = cursor.getBlob(cursor.getColumnIndex(DBHelper.COLUMN_IMAGE));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DESCRIPTION));
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                photographs.add(new Photograph(bitmap, description));
            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    public void addPhoto() {
        Intent intent = new Intent(this, foto.class);
        startActivityForResult(intent, REQUEST_ADD_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_PHOTO && resultCode == RESULT_OK) {
            byte[] imageBytes = data.getByteArrayExtra("image");
            String description = data.getStringExtra("description");
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            insertPhotograph(imageBytes, description);
            photographs.add(new Photograph(bitmap, description));
            adapter.notifyDataSetChanged();
        }
    }

    private void insertPhotograph(byte[] image, String description) {
        dbHelper.insertPhotograph(image, description);
    }
}
