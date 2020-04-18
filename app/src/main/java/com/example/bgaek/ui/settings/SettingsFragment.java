package com.example.bgaek.ui.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.bgaek.R;
import com.example.bgaek.WorkStyle;

public class SettingsFragment  extends Fragment {

    Switch switchStyle;
    WorkStyle workStyle, workStyle2;
    int status;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        switchStyle = (Switch)root.findViewById(R.id.switchStyle);

        //Сделать переключение стилей
        //Сделать выход с аккаунта
        //Создать переключатель уведомлений
        // Меню помощи

        return root;
    }
}
