package com.example.bgaek;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class ResultActivity extends AppCompatActivity {

    Button buttonResTest, buttonResPractice, buttonResControlWork;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        buttonResPractice = findViewById(R.id.buttonResPractice);
        buttonResPractice = findViewById(R.id.buttonResControlWork);
        buttonResPractice = findViewById(R.id.buttonResTest);

        webView = findViewById(R.id.webViewRes);

        buttonResTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("file:///android_asset/mypage.html");
            }
        });
        buttonResControlWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("file:///android_asset/mypage.html");
            }
        });
        buttonResPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("file:///android_asset/mypage.html");
            }
        });
    }
}
