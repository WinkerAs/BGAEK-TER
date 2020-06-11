package com.example.bgaek.ui.trainers;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bgaek.R;

import java.net.URI;

public class TrainersFragment extends Fragment {

    ImageView imageViewTrainer;
    Button buttonNextTrainer, buttonPrevios, buttonCheck;
    EditText editTextAnswerTrainer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_trainers, container, false);

        imageViewTrainer = root.findViewById(R.id.imageViewTrainer);
        buttonNextTrainer = root.findViewById(R.id.buttonNextTrainer);
        buttonPrevios = root.findViewById(R.id.buttonPrevious);
        buttonCheck = root.findViewById(R.id.buttonCheck);
        editTextAnswerTrainer = root.findViewById(R.id.editTextAnswerTrainer);

        final String[] answers = {"120", "2", "5", "720", "12", "35", "90", "21", "6","3", "696", "6", "30", "80", "32", "41", "900", "7", "14400", "20",
                "2", "6", "120", "720", "1", "1", "5040", "24", "40320", "362880",
                "96", "30", "6", "120", "1", "7", "21", "6", "8", "121"};


        final int[] masImage = {R.drawable.z1, R.drawable.z2, R.drawable.z3, R.drawable.z4, R.drawable.z5, R.drawable.z6, R.drawable.z7, R.drawable.z8, R.drawable.z9, R.drawable.z10,
                R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a6, R.drawable.a7, R.drawable.a8, R.drawable.a9, R.drawable.a10,
        R.drawable.z11, R.drawable.z12, R.drawable.z13, R.drawable.z14, R.drawable.z15, R.drawable.z16, R.drawable.z17, R.drawable.z18, R.drawable.z19, R.drawable.z20,
                R.drawable.z21, R.drawable.z22, R.drawable.z23, R.drawable.z24, R.drawable.z25, R.drawable.z26, R.drawable.z27, R.drawable.z28, R.drawable.z29, R.drawable.z30};
        imageViewTrainer.setImageResource(masImage[0]);
        buttonNextTrainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count < answers.length-1){
                    count++;
                    editTextAnswerTrainer.setText("");
                    imageViewTrainer.setImageResource(masImage[count]);
                }
            }
        });
        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkQuestion(answers);
            }
        });
        buttonPrevios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count > 0){
                    count--;
                    editTextAnswerTrainer.setText("");
                    imageViewTrainer.setImageResource(masImage[count]);
                }
            }
        });

        return root;
    }
    int count = 0;
    void checkQuestion(String[] masAnswer){
        if (count < masAnswer.length){
            if (editTextAnswerTrainer.getText().toString().equals(masAnswer[count])){
                Toast.makeText(getActivity(), "Правильный ответ", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getActivity(), "Неправильный ответ", Toast.LENGTH_LONG).show();
            }

        }else{

        }

    }
}