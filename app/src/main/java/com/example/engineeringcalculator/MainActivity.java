package com.example.engineeringcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.engineering_calculator);

        InitView();
    }

    private void InitView() {
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
