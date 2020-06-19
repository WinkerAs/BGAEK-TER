package com.example.bgaek;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bgaek.ui.task_test.TaskTestFragment;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TestActivity extends AppCompatActivity {

    Toolbar toolbar;
    String urlDemo = "https://bgaek.000webhostapp.com/getDataTestDemo.php";
    String urlAnswer = "https://bgaek.000webhostapp.com/getDataTest.php";
    String URL_FOR_Result = "https://bgaek.000webhostapp.com/addResultTest.php";
    String idTest, idStudent, answer;
    Button buttonNextTask;
    TextView textViewTaskTest;
    int count = 0;
    RadioButton radioButtonAnswer, radioButtonAnswer2, radioButtonAnswer3, radioButtonAnswer4;
    int Test;
    String[] masAnswer, masAnswer2, masAnswer3, masAnswer4, masTest, masNameTask;
    ArrayList<String> listAnswer, listAnswer2, listAnswer3, listAnswer4, listNameTask;
    RadioGroup radioGroupTest;
    int[] mas;
    int point = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //getWindow().setBackgroundDrawableResource(R.drawable.books_menu);
        toolbar =  findViewById(R.id.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        buttonNextTask = (Button)findViewById(R.id.buttonNextTask);
        textViewTaskTest = (TextView) findViewById(R.id.textViewTaskTest);
        radioButtonAnswer = findViewById(R.id.radioButtonAnswer);
        radioButtonAnswer2 = findViewById(R.id.radioButtonAnswer2);
        radioButtonAnswer3 = findViewById(R.id.radioButtonAnswer3);
        radioButtonAnswer4 = findViewById(R.id.radioButtonAnswer4);
        radioGroupTest = findViewById(R.id.radioGroupTest);

        idStudent = getIntent().getExtras().getString("id_student");
        idTest = getIntent().getExtras().getString("idTest");

        loadTest();

        buttonNextTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer = listAnswer.get(count);
                if(radioButtonAnswer.isChecked()){
                    if (radioButtonAnswer.getText().equals(answer)){
                        addMark();
                    }
                }else if(radioButtonAnswer2.isChecked()){
                    if (radioButtonAnswer2.getText().equals(answer)){
                        addMark();
                    }
                }else if(radioButtonAnswer3.isChecked()){
                    if (radioButtonAnswer3.getText().equals(answer)){
                        addMark();
                    }
                }else if(radioButtonAnswer4.isChecked()){
                    if (radioButtonAnswer4.getText().equals(answer)){
                        addMark();
                    }
                }
                count++;
                if(count < listAnswer.size()){
                    textViewTaskTest.setText(listNameTask.get(count));
                    RandomAnswer();
                }else{
                    addResultTest(idStudent, masNameTask[0], String.valueOf(point));
                    finish();
                    Toast.makeText(getApplicationContext(), "Количество правильных ответов: "+point, Toast.LENGTH_LONG).show();

                }

                clearCheck();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    int[] masArrayTest, masArrayAnswer, masArrayAnswer2;
    String nameTest;
    int[] masNamePractice;
    String[] masName;

    public void loadTest(){
        listAnswer = new ArrayList<>();
        listAnswer2 = new ArrayList<>();
        listAnswer3 = new ArrayList<>();
        listAnswer4 = new ArrayList<>();
        listNameTask = new ArrayList<>();
        masArrayTest = new int[]{R.array.Testq1, R.array.Testq2};
        masArrayAnswer = new int[]{R.array.Testq1v1, R.array.Testq1v2,  R.array.Testq1v3,  R.array.Testq1v4};
        masArrayAnswer2 = new int[]{R.array.Testq2v1, R.array.Testq2v2,  R.array.Testq2v3,  R.array.Testq2v4};

        switch (idTest){
            case "0":
                masAnswer = getResources().getStringArray(masArrayAnswer[0]);
                masAnswer2 = getResources().getStringArray(masArrayAnswer[1]);
                masAnswer3 = getResources().getStringArray(masArrayAnswer[2]);
                masAnswer4 = getResources().getStringArray(masArrayAnswer[3]);
                masNameTask = getResources().getStringArray(masArrayTest[Integer.parseInt(idTest)]);
                break;
            case "1":
                masAnswer = getResources().getStringArray(masArrayAnswer2[0]);
                masAnswer2 = getResources().getStringArray(masArrayAnswer2[1]);
                masAnswer3 = getResources().getStringArray(masArrayAnswer2[2]);
                masAnswer4 = getResources().getStringArray(masArrayAnswer2[3]);
                masNameTask = getResources().getStringArray(masArrayTest[Integer.parseInt(idTest)]);
                break;
        }

        for (int i = 0; i < masAnswer.length; i++) {
            listAnswer.add(masAnswer[i]);
            listAnswer2.add(masAnswer2[i]);
            listAnswer3.add(masAnswer3[i]);
            listAnswer4.add(masAnswer4[i]);
            listNameTask.add(masNameTask[i]);
        }

        textViewTaskTest.setText(listNameTask.get(count));
        RandomAnswer();
    }

    private void addResultTest(final String id_student, final String testText, final String mark) {
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
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_student", id_student);
                params.put("testText", testText);
                params.put("mark", mark);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, cancel_req_tag);
    }

    public void addMark(){
        point++;
    }

    public void clearCheck(){
        radioGroupTest.clearCheck();
    }

    public void RandomAnswer(){
        Random random = new Random();
        int xRand = random.nextInt(4) + 1;
        if (xRand == 1){
            radioButtonAnswer.setText(listAnswer3.get(count));
            radioButtonAnswer2.setText(listAnswer2.get(count));
            radioButtonAnswer3.setText(listAnswer.get(count));
            radioButtonAnswer4.setText(listAnswer4.get(count));
        }else if(xRand == 2){
            radioButtonAnswer.setText(listAnswer.get(count));
            radioButtonAnswer2.setText(listAnswer3.get(count));
            radioButtonAnswer3.setText(listAnswer2.get(count));
            radioButtonAnswer4.setText(listAnswer4.get(count));
        }else if(xRand == 3){
            radioButtonAnswer.setText(listAnswer4.get(count));
            radioButtonAnswer2.setText(listAnswer2.get(count));
            radioButtonAnswer3.setText(listAnswer.get(count));
            radioButtonAnswer4.setText(listAnswer3.get(count));
        }else if(xRand == 4){
            radioButtonAnswer.setText(listAnswer3.get(count));
            radioButtonAnswer2.setText(listAnswer4.get(count));
            radioButtonAnswer3.setText(listAnswer2.get(count));
            radioButtonAnswer4.setText(listAnswer.get(count));
        }
    }
}
