package com.example.bgaek.ui.tests_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.bgaek.R;

public class TestsListFragment extends Fragment {//Тесты на прохождение

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tests, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        return root;
    }
}