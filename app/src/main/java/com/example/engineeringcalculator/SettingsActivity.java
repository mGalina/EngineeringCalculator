package com.example.engineeringcalculator;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;

import static com.example.engineeringcalculator.R.*;

public class SettingsActivity extends AppCompatActivity {

    private EditText mInputImage;

    private static final String IMAGE_RESULT = "IMAGE_RESULT";
    private static final int REQUEST_CODE_PERMISSION_READ_STORAGE = 100;

    public static String readResult(Intent intent) {
        return intent.getStringExtra(IMAGE_RESULT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_settings);

        initViews();
    }

    private void loadImage() {
        if (isExternalStorageReadable()) {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), mInputImage.getText().toString());
            if (!file.exists()) {
                Toast.makeText(SettingsActivity.this, "Файл не найден", Toast.LENGTH_LONG).show();
                return;
            }
            setResult(RESULT_OK, new Intent().putExtra(IMAGE_RESULT, file.getAbsolutePath()));
            finish();
        }
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_PERMISSION_READ_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadImage();
            } else {
                Toast.makeText(SettingsActivity.this, "Нужны разрешения", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void initViews() {
        mInputImage = findViewById(id.input_image);
        Button mSaveImage = findViewById(id.btn_ok);

        mSaveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permissionStatus = ContextCompat.checkSelfPermission(SettingsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
                if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                    loadImage();
                } else {
                    ActivityCompat.requestPermissions(SettingsActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_PERMISSION_READ_STORAGE);
                }
            }
        });
    }
}