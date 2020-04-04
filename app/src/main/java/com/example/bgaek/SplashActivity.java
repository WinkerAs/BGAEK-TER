package com.example.bgaek;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = URLsConnection.TAG_SPLASHACTIVITY;
    private static final String URL_FOR_LOGIN = URLsConnection.URL_FOR_LOGIN;

    private final static String FILE_NAME = URLsConnection.FILE_LOGIN;
    private final static String FILE_CHECK = URLsConnection.FILE_CHECK;

    String isChekedText, emailText, passwordText;
    private static final int PERMISSION_REQUEST_CODE = 123;
    int SPLASH_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        File fileUser = new File(getFilesDir() +"/"+FILE_NAME);
        File fileCheck = new File(getFilesDir() +"/"+ FILE_CHECK);

        if (hasPermissions()){
            // our app has permissions.
            makeFolder();
        }
        else {
            //our app doesn't have permissions, So i m requesting permissions.
            requestPermissionWithRationale();
        }

        if (!fileCheck.exists()){
            Log.d(TAG, "Файл не существует, поэтому был создан");
            saveCheckBox();
        }

        if (!fileUser.exists()){
            saveText();
        }

        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileNetwork = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (mWifi.isConnected() || mobileNetwork.isConnected()) {
             openTextCheckBox();
            if (isChekedText.equals("1")){
               openTextSign();
               loginUser(emailText,
                        passwordText);
            }
            else{
                startAcivityLogin();
            }
        }else{
            Intent intent = new Intent(this, NotConnectWifiActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }
    }

    private void makeFolder(){
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"fandroid");

        if (!file.exists()){
            Boolean ff = file.mkdir();
            if (ff){
                Toast.makeText(SplashActivity.this, "Folder created successfully", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(SplashActivity.this, "Failed to create folder", Toast.LENGTH_SHORT).show();
            }

        }
        else {
            //Toast.makeText(SplashActivity.this, "Folder already exist", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean hasPermissions(){
        int res = 0;
        //string array of permissions,
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        for (String perms : permissions){
            res = checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)){
                return false;
            }
        }
        return true;
    }

    private void requestPerms(){
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permissions,PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed = true;

        switch (requestCode){
            case PERMISSION_REQUEST_CODE:

                for (int res : grantResults){
                    // if user granted all permissions.
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }

                break;
            default:
                // if user not granted permissions.
                allowed = false;
                break;
        }

        if (allowed){
            //user granted all permissions we can perform our task.
            makeFolder();
        }
        else {
            // we will give warning to user that they haven't granted permissions.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    Toast.makeText(this, "Storage Permissions denied.", Toast.LENGTH_SHORT).show();

                } else {
                    showNoStoragePermissionSnackbar();
                }
            }
        }

    }

    public void showNoStoragePermissionSnackbar() {
        Snackbar.make(SplashActivity.this.findViewById(R.id.activity_view), "Storage permission isn't granted" , Snackbar.LENGTH_LONG)
                .setAction("SETTINGS", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openApplicationSettings();

                        Toast.makeText(getApplicationContext(),
                                "Open Permissions and grant the Storage permission",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                })
                .show();
    }

    public void openApplicationSettings() {
        Intent appSettingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(appSettingsIntent, PERMISSION_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            makeFolder();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void requestPermissionWithRationale() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            final String message = "Storage permission is needed to show files count";
            Snackbar.make(SplashActivity.this.findViewById(R.id.activity_view), message, Snackbar.LENGTH_LONG)
                    .setAction("GRANT", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            requestPerms();
                        }
                    })
                    .show();
        } else {
            requestPerms();
        }
    }


    public void startAcivityLogin(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mySuperIntent = new Intent(SplashActivity.this, LoginActivity.class);
                //Do any action here. Now we are moving to next page
                startActivity(mySuperIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                //This 'finish()' is for exiting the app when back button pressed from Home page which is ActivityHome
                finish();
            }
        }, SPLASH_TIME);
    }

    private void loginUser( final String email, final String password) {
        // Tag used to cancel the request
        String cancel_req_tag = "login";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {

                        //user = jObj.getJSONObject("user").getString("name");


                        Intent intent = new Intent(
                                SplashActivity.this,
                                MainActivity.class);
                        //intent.putExtra("id_author", id_author);

                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                        // Toast.makeText(getApplicationContext(), "Добро пожаловать, "+user+"!", Toast.LENGTH_SHORT).show();
                        // This 'finish()' is for exiting the app when back button pressed from Home page which is ActivityHome
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
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,cancel_req_tag);
    }

    public void openTextCheckBox(){

        FileInputStream fin = null;

        try {
            fin = openFileInput(FILE_CHECK);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String (bytes);
            isChekedText = text;
            //Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        }
        catch(IOException ex) {
            // Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally{
            try{
                if(fin!=null)
                    fin.close();
            }
            catch(IOException ex){
                // Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void saveCheckBox(){
        FileOutputStream fos = null;
        try {

            String text;
            text = "000";

            fos = openFileOutput(FILE_CHECK, MODE_PRIVATE);
            fos.write(text.getBytes());
        }
        catch(IOException ex) {
            //Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally{
            try{
                if(fos!=null)
                    fos.close();
            }
            catch(IOException ex){
                //Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void openTextSign(){

        FileInputStream fin = null;

        try {
            fin = openFileInput(FILE_NAME);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String (bytes);
            String[] masText = text.split("\n");

            emailText = masText[0];
            passwordText = masText[1];

            //Toast.makeText(this, "Добро пожаловать", Toast.LENGTH_SHORT).show();
        }
        catch(IOException ex) {
            // Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally{
            try{
                if(fin!=null)
                    fin.close();
            }
            catch(IOException ex){
                //Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void saveText(){

        FileOutputStream fos = null;
        try {

            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write("".getBytes());
            //Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
        }
        catch(IOException ex) {
            //Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally{
            try{
                if(fos!=null)
                    fos.close();
            }
            catch(IOException ex){
                //Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
          }
        }
    }
}
