package com.example.bgaek.ui.answer_practice;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bgaek.AppSingleton;
import com.example.bgaek.LoginActivity;
import com.example.bgaek.PracticeActivity;
import com.example.bgaek.R;
import com.example.bgaek.RecyclerViewAdapterTest;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnswerPracticeFragment extends Fragment {
    String urlAnswer = "https://bgaek.000webhostapp.com/getDataAnswerPractice.php";
    String URL_FOR_Result = "https://bgaek.000webhostapp.com/addResultPractice.php";
    String studentVariant, idPractice, idStudent;
    Button buttonNextAnswer;
    EditText editTextAnswer;
    TextView textView12;
    int count = 0;
    ArrayList<String> listAnswer = new ArrayList<String>();
    ArrayList<String> listPractice = new ArrayList<String>();
    ArrayList<String> listVariant = new ArrayList<String>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_answer_practice, container, false);
        //setRetainInstance(true);
        buttonNextAnswer = (Button)root.findViewById(R.id.buttonNextAnswer);
        editTextAnswer = (EditText)root.findViewById(R.id.editTextAnswer);
        textView12 = (TextView) root.findViewById(R.id.textViewQ);

        idStudent = getActivity().getIntent().getExtras().getString("id_student");

        studentVariant = getActivity().getIntent().getExtras().getString("variant");
        idPractice = "1";

        MyTask myTask = new MyTask();
        myTask.execute();

        buttonNextAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count < masAnswer.length-1){
                    if (masAnswer[count].equals(editTextAnswer.getText().toString()))
                        addAnswer(idStudent, idPractice, masTask[count], "1");
                    count++;
                }else
                    getActivity().finish();
            }
        });
        return root;
    }

    String[] masAnswer, masVariant, masPractice, masTask;

    class MyTask extends AsyncTask<Void, Void, Void> {

        String practice, answer, variant, task;//Тут храним значение заголовка сайта

        @Override
        protected Void doInBackground(Void... params) {

            Document doc = null;//Здесь хранится будет разобранный html документ

            try {
                doc = Jsoup.connect(urlAnswer).get();

                answer = doc.select("b").text();
                variant = doc.select("h1").text();
                practice = doc.select("h2").text();
                task = doc.select("h3").text();

            } catch (IOException e) {
                //Если не получилось считать
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            masAnswer = answer.split(";");
            masVariant = variant.split(";");
            masPractice = practice.split(";");
            masTask = task.split(";");

        }
    }

    private void addAnswer(final String id_student, final String id_practice, final String id_answer, final String mark) {
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
                params.put("id_answer", id_answer);
                params.put("mark", mark);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(strReq, cancel_req_tag);
    }
}