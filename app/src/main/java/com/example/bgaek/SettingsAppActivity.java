package com.example.bgaek;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

public class SettingsAppActivity extends AppCompatActivity {

    Switch switchStyle;
    Toolbar toolbar;
    WorkStyle workStyle, workStyle2;
    int status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_app);
        toolbar =  findViewById(R.id.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        switchStyle = (Switch)findViewById(R.id.switchStyle);
        workStyle = new WorkStyle(this);

        //Сделать переключение стилей
        //Сделать выход с аккаунта
        //Создать переключатель уведомлений
        // Меню помощи
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
