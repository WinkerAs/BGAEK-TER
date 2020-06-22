package com.example.bgaek.ui.answer_practice;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bgaek.AppSingleton;
import com.example.bgaek.LoginActivity;
import com.example.bgaek.PracticeActivity;
import com.example.bgaek.R;
import com.example.bgaek.RecyclerViewAdapterTest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnswerPracticeFragment extends Fragment {
    String URL_FOR_Result = "https://bgaek.000webhostapp.com/addResultPractice.php";
    String studentVariant, idPractice, idStudent;
    Button buttonNextAnswer;
    EditText editTextAnswer;
    TextView textView12, textViewVariant, textViewQuestion;
    int count = 0, idPracticeDigit;
    ArrayList<String> listAnswer = new ArrayList<String>();
    ArrayList<String> listPractice = new ArrayList<String>();
    ArrayList<String> listVariant = new ArrayList<String>();
    int point;
    RequestQueue requestQueue;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_answer_practice, container, false);
        //setRetainInstance(true);
        buttonNextAnswer = (Button)root.findViewById(R.id.buttonNextAnswer);
        editTextAnswer = (EditText)root.findViewById(R.id.editTextAnswer);
        textView12 = (TextView) root.findViewById(R.id.textViewQ);
        textViewQuestion = root.findViewById(R.id.textViewQuestion);
        textViewVariant = root.findViewById(R.id.textViewVariant);
        requestQueue = Volley.newRequestQueue(getActivity());

        idPractice = getActivity().getIntent().getExtras().getString("idPractice");
        idPracticeDigit = Integer.parseInt(idPractice) + 1;
        idStudent = getActivity().getIntent().getExtras().getString("id_student");
        studentVariant = getActivity().getIntent().getExtras().getString("variant");
        point = 0;
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
        addAnswer(idStudent, String.valueOf(idPracticeDigit), masName[count], "1");
    }
    int[] masArrayPracticeAnswer, masArrayPracticeAnswer2, masArrayPracticeAnswer3, masArrayPracticeAnswer4, masArrayPracticeAnswer5;
    int[] masNamePractice;
    String[] masName, masAnswer;
    public void loadData(){
        masArrayPracticeAnswer = new int[]{R.array.PR1v1, R.array.PR1v2, R.array.PR1v3, R.array.PR1v4, R.array.PR1v5, R.array.PR1v6, R.array.PR1v7, R.array.PR1v8, R.array.PR1v9, R.array.PR1v10};
        masArrayPracticeAnswer2 = new int[]{R.array.PR2v1, R.array.PR2v2, R.array.PR2v3, R.array.PR2v4, R.array.PR2v5, R.array.PR2v6, R.array.PR2v7, R.array.PR2v8, R.array.PR2v9, R.array.PR2v10};
        masArrayPracticeAnswer3 = new int[]{R.array.PR3v1, R.array.PR3v2, R.array.PR3v3, R.array.PR3v4, R.array.PR3v5, R.array.PR3v6, R.array.PR3v7, R.array.PR3v8, R.array.PR3v9, R.array.PR3v10};
        masArrayPracticeAnswer4 = new int[]{R.array.PR4v1, R.array.PR4v2, R.array.PR4v3, R.array.PR4v4, R.array.PR4v5, R.array.PR4v6, R.array.PR4v7, R.array.PR4v8, R.array.PR4v9, R.array.PR4v10};
        masArrayPracticeAnswer5 = new int[]{R.array.PR1v1, R.array.PR1v2, R.array.PR1v3, R.array.PR1v4, R.array.PR1v5, R.array.PR1v6, R.array.PR1v7, R.array.PR1v8, R.array.PR1v9, R.array.PR1v10};
        masNamePractice = new int[]{R.array.PracticeName1, R.array.PracticeName2, R.array.PracticeName3, R.array.PracticeName4};

        switch (idPractice){
            case "0":
                masName = getResources().getStringArray(masNamePractice[0]);
                masAnswer = getResources().getStringArray(masArrayPracticeAnswer[Integer.parseInt(studentVariant)-1]);
                break;
            case "1":
                masName = getResources().getStringArray(masNamePractice[1]);
                masAnswer = getResources().getStringArray(masArrayPracticeAnswer2[Integer.parseInt(studentVariant)-1]);
                break;
            case "2":
                masName = getResources().getStringArray(masNamePractice[2]);
                masAnswer = getResources().getStringArray(masArrayPracticeAnswer3[Integer.parseInt(studentVariant)-1]);
                break;
            case "3":
                masName = getResources().getStringArray(masNamePractice[3]);
                masAnswer = getResources().getStringArray(masArrayPracticeAnswer4[Integer.parseInt(studentVariant)-1]);
                break;
            case "4":
                masName = getResources().getStringArray(masNamePractice[4]);
                masAnswer = getResources().getStringArray(masArrayPracticeAnswer5[Integer.parseInt(studentVariant)-1]);
                break;
            case "5":
                masName = getResources().getStringArray(masNamePractice[5]);
                masAnswer = getResources().getStringArray(masArrayPracticeAnswer[Integer.parseInt(studentVariant)-1]);
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
                params.put("id_practice", id_practice);
                params.put("answerText", answerText);
                params.put("mark", mark);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(strReq, cancel_req_tag);
    }
}