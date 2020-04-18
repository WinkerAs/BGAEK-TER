package com.example.bgaek.ui.greeting_window;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bgaek.R;
import com.example.bgaek.ui.tasks_practice.TaskPracticeFragment;

public class GreetingFragment extends Fragment {

    Button buttonBeginPractice;
    TextView textViewGreeting;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_greeting, container, false);

        buttonBeginPractice = root.findViewById(R.id.buttonBeginPractice);
        textViewGreeting = root.findViewById(R.id.textViewGreeting);

        buttonBeginPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonBeginPractice.setVisibility(Button.INVISIBLE);
                textViewGreeting.setVisibility(TextView.INVISIBLE);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment, new TaskPracticeFragment())
                        .commit();
            }
        });

        return root;
    }
}
