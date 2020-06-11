package com.example.bgaek;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NotConnectWifiActivity extends AppCompatActivity {

    Button buttonRepeatConnection;
    TextView textViewNotConnectionWifi;
    WorkStyle workStyle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_connect_wifi);

        workStyle = new WorkStyle(this);
        textViewNotConnectionWifi = (TextView)findViewById(R.id.textViewNotConnectionWifi);
        textViewNotConnectionWifi.setText("Проверьте Ваши настройки доступа\nк интернету или попробуйте\nзайти позже");
        buttonRepeatConnection = (Button)findViewById(R.id.buttonRepeatConnection);
        buttonRepeatConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotConnectWifiActivity.this, SplashActivity.class);
                startActivity(intent);
                workStyle.animationActivity();
                finish();
            }
        });

    }
}
