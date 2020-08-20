package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aoyou.daterangeprovider.DateRander;
import com.aoyou.daterangeprovider.IDateSelectListening;
import com.aoyou.daterangeprovider.SelectedResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        setStatusBarDarkMode();

    }

    @Override
    protected void initData() {

    }

    public void goToCalendar(View view) {
//        Intent intent = new Intent(this, RangeActivity.class);
//        startActivity(intent);

        RangeActivity.show(this);
    }

    public void goToCalendar2(View view) {

        RangeNewActivity.show(this);
    }
}