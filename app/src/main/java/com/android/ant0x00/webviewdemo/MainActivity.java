package com.android.ant0x00.webviewdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private Button btn;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);
        btn = findViewById(R.id.button);

        Context context = getApplicationContext();
        sharedPref = context.getSharedPreferences(
                getString(R.string.login_or_not), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.login_or_not), false);
        editor.commit();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!sharedPref.getBoolean(getString(R.string.login_or_not), false)) {
                    Intent intent = new Intent(MainActivity.this, CheckLogin.class);
                    startActivityForResult(intent, 100);
                }
            }
        });

        loadWebContent();
    }

    private void loadWebContent() {
        webView.setWebViewClient(new WebViewClient() {
            //设置在webView点击打开的新网页在当前界面显示,而不跳转到新的浏览器中
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);

        if (sharedPref.getBoolean(getString(R.string.login_or_not), false)) {
            webView.loadUrl("https://cn.bing.com");
        } else {
            webView.loadUrl("https://developer.android.google.cn");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode ==100){
            loadWebContent();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
