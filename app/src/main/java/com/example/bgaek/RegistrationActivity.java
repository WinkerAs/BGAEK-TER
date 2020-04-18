package com.example.bgaek;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private static final String URL_FOR_REGISTRATION = URLsConnection.URL_FOR_REGISTRATION;
    ProgressDialog progressDialog;

    private EditText signupInputName, signupInputLogin, signupInputPassword;
    private Button btnSignUp;
    private Button btnLinkLogin;
    WorkDialog workDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        workDialog = new WorkDialog(progressDialog);
        signupInputName = findViewById(R.id.signup_input_name);
        signupInputLogin = findViewById(R.id.signup_input_login);
        signupInputPassword = findViewById(R.id.signup_input_password);

        btnSignUp = findViewById(R.id.btn_signup);
        btnLinkLogin = findViewById(R.id.btn_link_login);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (signupInputName.length() > 8 && signupInputLogin.length() > 3 && signupInputPassword.length() > 5){
                    submitForm();
                }else{
                    Toast.makeText(getApplicationContext(),"Некоторые поля не соответсвуют требованиям",Toast.LENGTH_LONG).show();
                }
            }
        });


        btnLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void submitForm() {
        registerUser(signupInputName.getText().toString(),
                signupInputLogin.getText().toString(),
                signupInputPassword.getText().toString());
    }

    private void registerUser(final String fio,  final String login, final String password) {
        // Tag used to cancel the request
        String cancel_req_tag = "register";
        workDialog.showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_REGISTRATION, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
                workDialog.hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        String user = jObj.getJSONObject("user").getString("name");
                        Toast.makeText(getApplicationContext(), "Здравствуй " + user +", Вы успешно добавлены!", Toast.LENGTH_SHORT).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                getApplicationContext(),
                                LoginActivity.class);
                        startActivity(intent);
                        finish();
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
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                workDialog.hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", fio);
                params.put("email", login);
                params.put("password", password);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, cancel_req_tag);
    }
}
