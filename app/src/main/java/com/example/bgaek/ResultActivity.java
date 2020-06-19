package com.example.bgaek;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class ResultActivity extends AppCompatActivity {

    Button buttonResTest, buttonResPractice;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        buttonResPractice = findViewById(R.id.buttonResPractice);
        buttonResTest = findViewById(R.id.buttonResTest);

        webView = findViewById(R.id.webViewRes);
        webView.loadUrl("https://bgaek.000webhostapp.com/getResTest.php");
        buttonResTest.setEnabled(false);
        buttonResTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonResTest.setEnabled(false);
                buttonResPractice.setEnabled(true);
                webView.loadUrl("https://bgaek.000webhostapp.com/getResTest.php");
            }
        });

        buttonResPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonResTest.setEnabled(true);
                buttonResPractice.setEnabled(false);
                webView.loadUrl("https://bgaek.000webhostapp.com/getRes.php");
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
