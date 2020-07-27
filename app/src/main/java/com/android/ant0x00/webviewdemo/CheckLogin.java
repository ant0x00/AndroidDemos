package com.android.ant0x00.webviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CheckLogin extends AppCompatActivity {

    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_login);
        Button login = findViewById(R.id.login);


        Context context = getApplicationContext();
        sharedPref = context.getSharedPreferences(
                getString(R.string.login_or_not), Context.MODE_PRIVATE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(getString(R.string.login_or_not), true);
                editor.commit();
                Intent d = getIntent();
                setResult(100, d);
                finish();
            }
        });
    }
}
