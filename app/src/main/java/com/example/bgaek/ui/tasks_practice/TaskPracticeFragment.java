package com.example.bgaek.ui.tasks_practice;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bgaek.R;

public class TaskPracticeFragment extends Fragment {

    TextView textViewTask;
    Button buttonNextTask;
    EditText editTextAnswer;
    int count, points, answer;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tasks_practice, container, false);

        textViewTask = root.findViewById(R.id.textViewTask);
        buttonNextTask = root.findViewById(R.id.buttonNextTask);
        editTextAnswer = root.findViewById(R.id.editTextAnswer);

        final String[] masTask = {"Задание 1", "Задание 2", "Задание 3"};
        final String[] masAnswer = {"12", "23", "34"};

        textViewTask.setText(masTask[0]);
        count = 0;

        buttonNextTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (masAnswer[count].equals(editTextAnswer.getText())){
                    points++;
                }
                count++;
                textViewTask.setText(masTask[count]);
                editTextAnswer.setText("");
            }
        });

        return root;
    }
}
