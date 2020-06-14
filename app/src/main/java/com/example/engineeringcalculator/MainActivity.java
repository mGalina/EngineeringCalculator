package com.example.engineeringcalculator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int GET_IMAGE_REQUEST_CODE = 9230;
    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.engineering_calculator);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        InitView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calculator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(MainActivity.this, R.string.settings, Toast.LENGTH_LONG).show();
                Intent intentSetting = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivityForResult(intentSetting, GET_IMAGE_REQUEST_CODE);
                return true;
            case R.id.action_exit:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(SettingsActivity.readResult(data));
            mImage.setImageBitmap(bitmap);
        }
    }

    private void InitView() {
        mImage = findViewById(R.id.imageView);
        final TextView resultField = findViewById(R.id.resultField);

        Button numeral1 = findViewById(R.id.bt_1);
        numeral1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultField.setText(getText(R.string._1));
            }
        });
    }
}