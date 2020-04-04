package com.example.bgaek.ui.base_info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.bgaek.R;

public class SaveInformationFragment extends Fragment {//Сборник информации загружаемый из БД (В бд хранятся ссылки на файлы и картинки)

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_base_info, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        return root;
    }
}