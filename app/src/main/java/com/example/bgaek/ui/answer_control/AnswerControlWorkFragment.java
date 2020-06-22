package com.example.bgaek.ui.answer_control;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bgaek.AppSingleton;
import com.example.bgaek.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnswerControlWorkFragment extends Fragment {
    String URL_FOR_Result = "https://bgaek.000webhostapp.com/addResultControlWork.php";
    String idControl, idStudent, studentVariant;
    Button buttonNextAnswer;
    EditText editTextAnswer;
    TextView textViewQuestion, textViewVariant;
    int count = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_answer_control, container, false);
        //setRetainInstance(true);
        buttonNextAnswer = (Button)root.findViewById(R.id.buttonNextAnswerControl);
        editTextAnswer = (EditText)root.findViewById(R.id.editTextAnswerControl);
        textViewQuestion = root.findViewById(R.id.textViewQuestionControl);
        textViewVariant = root.findViewById(R.id.textViewVariantControl);

        idControl = getActivity().getIntent().getExtras().getString("idControl");
        idStudent = getActivity().getIntent().getExtras().getString("id_student");
        studentVariant = getActivity().getIntent().getExtras().getString("variant");
        loadData();
        textViewVariant.setText("Ваш вариант: "+studentVariant);
        textViewQuestion.setText(masName[count]);

        buttonNextAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count < masAnswer.length-1){
                    if (masAnswer[count].equals(editTextAnswer.getText().toString())){
                        addMark();
                        Toast.makeText(getActivity(), "Верный ответ", Toast.LENGTH_SHORT).show();
                        editTextAnswer.setText("");
                        point++;
                    }
                    count++;
                    textViewQuestion.setText(masName[count]);
                }else{
                    Toast.makeText(getActivity(), "Количество правильных ответов "+String.valueOf(point)+" из "+masAnswer.length, Toast.LENGTH_LONG).show();
                    getActivity().finish();
                }

            }
        });
        return root;
    }

    private void addMark(){
        addAnswer(idStudent, idControl, masName[count], "1");
    }
    int[] masArrayPracticeAnswer;
    int[] masNamePractice;
    String[] masName, masAnswer;
    int point;
    public void loadData(){
        masArrayPracticeAnswer = new int[]{R.array.Controlz1, R.array.Controlz2};
        masNamePractice = new int[]{R.array.Controlname1, R.array.Controlname1,};

        switch (idControl){
            case "1":
                masName = getResources().getStringArray(masNamePractice[0]);
                masAnswer = getResources().getStringArray(masArrayPracticeAnswer[0]);
                break;
            case "2":
                masName = getResources().getStringArray(masNamePractice[1]);
                masAnswer = getResources().getStringArray(masArrayPracticeAnswer[1]);
                break;
        }
    }

    private void addAnswer(final String id_student, final String id_practice, final String answerText, final String mark) {
        // Tag used to cancel the request
        String cancel_req_tag = "register";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_Result, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        //String user = jObj.getJSONObject("user").getString("name");

                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getActivity(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_student", id_student);
                params.put("id_control_work", id_practice);
                params.put("answerText", answerText);
                params.put("mark", mark);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(strReq, cancel_req_tag);
    }
}