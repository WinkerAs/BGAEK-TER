package com.example.bgaek.ui.task_test;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bgaek.AppSingleton;
import com.example.bgaek.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TaskTestFragment extends Fragment {
    String urlAnswer = "https://bgaek.000webhostapp.com/getDataTest.php";
    String URL_FOR_Result = "https://bgaek.000webhostapp.com/addResultTest.php";
    String idTest, idStudent;
    Button buttonNextTask;
    EditText editTextAnswer;
    TextView textViewTaskTest;
    int count = 0;
    RadioButton radioButtonAnswer, radioButtonAnswer2, radioButtonAnswer3, radioButtonAnswer4;
    ArrayList<String> listAnswer = new ArrayList<String>();
    ArrayList<String> listPractice = new ArrayList<String>();
    ArrayList<String> listVariant = new ArrayList<String>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_task_test, container, false);
        //setRetainInstance(true);
        buttonNextTask = (Button)root.findViewById(R.id.buttonNextTask);
        textViewTaskTest = (TextView) root.findViewById(R.id.textViewTaskTest);
        radioButtonAnswer = root.findViewById(R.id.radioButtonAnswer);
        radioButtonAnswer2 = root.findViewById(R.id.radioButtonAnswer2);
        radioButtonAnswer3 = root.findViewById(R.id.radioButtonAnswer3);
        radioButtonAnswer4 = root.findViewById(R.id.radioButtonAnswer4);

        idStudent = getActivity().getIntent().getExtras().getString("id_student");
        idTest = "1";

        MyTask myTask = new MyTask();
        myTask.execute();

        textViewTaskTest.setText(masNameTask[count]);
        radioButtonAnswer.setText(masAnswer[count]);
        radioButtonAnswer2.setText(masAnswer2[count]);
        radioButtonAnswer3.setText(masAnswer3[count]);
        radioButtonAnswer4.setText(masAnswer4[count]);

        buttonNextTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
            }
        });
        return root;
    }

    String[] masAnswer, masAnswer2, masAnswer3, masAnswer4, masTest, masNameTask;

    class MyTask extends AsyncTask<Void, Void, Void> {

        String test, name_task, answer, answer2, answer3, answer4;//Тут храним значение заголовка сайта

        @Override
        protected Void doInBackground(Void... params) {

            Document doc = null;//Здесь хранится будет разобранный html документ

            try {
                //Считываем заглавную страницу http://harrix.org
                doc = Jsoup.connect(urlAnswer).get();

                test = doc.select("b").text();
                name_task = doc.select("h1").text();
                answer = doc.select("h2").text();
                answer2 = doc.select("h3").text();
                answer3 = doc.select("h4").text();
                answer4 = doc.select("h5").text();


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
            masAnswer2 = answer2.split(";");
            masAnswer3 = answer3.split(";");
            masAnswer4 = answer4.split(";");
            masTest = test.split(";");
            masNameTask = name_task.split(";");

        }
    }

    private void addResultTest(final String id_student, final String id_test, final String mark) {
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
                params.put("id_test", id_test);
                params.put("mark", mark);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(strReq, cancel_req_tag);
    }
}