package com.example.bgaek;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class QuestionFragment extends Fragment {

    public QuestionFragment() {
        // Required empty public constructor
    }



    TextView textViewQuest;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        textViewQuest = view.findViewById(R.id.textViewQuest);
        String message = getArguments().getString("message");
        textViewQuest.setText(message);

        return view;
    }

}
