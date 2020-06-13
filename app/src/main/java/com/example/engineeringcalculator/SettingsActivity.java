package com.example.engineeringcalculator;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;

import static com.example.engineeringcalculator.R.*;

public class SettingsActivity extends AppCompatActivity {

    private EditText mInputImage;
    private Button mSaveImage;
    private ImageView mImage;

    public static final int REQUEST_CODE_PERMISSION_WRITE_STORAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_settings);

        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            LoadImg();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION_WRITE_STORAGE);
        }

        initViews();
    }

    private void LoadImg() {
        if (isExternalStorageReadable()) {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), String.valueOf(mInputImage));
            mImage = findViewById(id.imageView);
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            mImage.setImageBitmap(bitmap);
            Toast.makeText(SettingsActivity.this, "данные сохранены", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION_WRITE_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                LoadImg();
            } else {
                Toast.makeText(SettingsActivity.this, "фаил не выбран", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void initViews() {
        mInputImage = findViewById(id.input_image);
        mSaveImage = findViewById(id.btn_save);

        mSaveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isExternalStorageReadable();
            }
        });
    }
}
