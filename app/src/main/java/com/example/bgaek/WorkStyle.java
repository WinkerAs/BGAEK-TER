package com.example.bgaek;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
//ДОДЕЛАТЬ !!! **********************************************************
public class WorkStyle {

    AppCompatActivity appCompatActivity;
    String FILE_STYLE = URLsConnection.FILE_STYLE;
    String styleInFile;
    public WorkStyle(AppCompatActivity appCompatActivity){
        this.appCompatActivity = appCompatActivity;
    }

    public void styleDark(){
        appCompatActivity.getDelegate().setLocalNightMode(appCompatActivity.
                getDelegate().MODE_NIGHT_YES);
        File fileStyle = new File(appCompatActivity.getFilesDir() +"/"+FILE_STYLE);
        saveText();
    }

    public void styleLight(){
        appCompatActivity.getDelegate().setLocalNightMode(appCompatActivity.
                getDelegate().MODE_NIGHT_NO);
    }

    public void animationActivity(){
        appCompatActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void openText(){

        FileInputStream fin = null;

        try {
            fin = appCompatActivity.openFileInput(FILE_STYLE);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String (bytes);
            String[] masText = text.split("\n");

            styleInFile = masText[0];
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

            fos = appCompatActivity.openFileOutput(FILE_STYLE, Context.MODE_PRIVATE);
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
