package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView txtLeft = findViewById(R.id.txt_left);
        final TextView txtRight = findViewById(R.id.txt_right);
        DoubleSeekBar doubleSeekBar = findViewById(R.id.double_seek_bar);
        doubleSeekBar.setMinValue(0);
        doubleSeekBar.setMaxValue(12);
        doubleSeekBar.setOnChanged(new DoubleSeekBar.OnChanged() {
            @Override
            public void onChange(int leftValue, int rightValue) {
                txtLeft.setText(String.valueOf(leftValue*100));
                txtRight.setText(String.valueOf(rightValue*100));
            }
        });
    }
}