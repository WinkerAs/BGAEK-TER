package com.example.bgaek.ui.settings;

import android.app.AlertDialog;
import android.app.UiModeManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bgaek.LoginActivity;
import com.example.bgaek.R;
import com.example.bgaek.WorkDialog;
import com.example.bgaek.WorkStyle;

import java.io.FileOutputStream;
import java.io.IOException;

public class SettingsFragment  extends Fragment {

    Switch switchStyle;
    WorkStyle workStyle, workStyle2;
    int status;
    Button buttonExitUser;
    private final static String FILE_CHECK = "checkBoxStatusBGAEK.txt";
    AlertDialog.Builder ad;
    Context context;


    public View onCreateView(@NonNull final LayoutInflater inflater,
                            final ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_settings, container, false);

        switchStyle = (Switch)root.findViewById(R.id.switchStyle);
        buttonExitUser = (Button)root.findViewById(R.id.buttonExitUser);
        //Сделать переключение стилей
        //Сделать выход с аккаунта
        //Создать переключатель уведомлений
        // Меню помощи
        buttonExitUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad.show();
            }
        });

        context = getActivity();
        String title = "Вы уверены, что хотите выйти?";
        String button1String = "Нет";
        String button2String = "Да";

        ad = new AlertDialog.Builder(context);
        ad.setTitle(title);  // заголовок
        ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
            }
        });
        ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Intent mySuperIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(mySuperIntent);
                saveCheckBox(inflater, container);
                getActivity().finish();
            }
        });
        ad.setCancelable(true);

        switchStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchStyle.isChecked()){
                    Toast.makeText(getActivity(), "ffwf", Toast.LENGTH_SHORT).show();
                }else{

                }
            }
        });
        return root;
    }


    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Toast.makeText(getActivity(), "Отслеживание переключения: " + (isChecked ? "on" : "off"),
                Toast.LENGTH_SHORT).show();
    }



    public void saveCheckBox(@NonNull LayoutInflater inflater, ViewGroup container){

        FileOutputStream fos = null;
        try {

            String text;
            text = "0";

            fos = getActivity().openFileOutput(FILE_CHECK, Context.MODE_PRIVATE);

            fos.write(text.getBytes());
            Toast.makeText(getActivity(), "Выход из системы", Toast.LENGTH_SHORT).show();
        }
        catch(IOException ex) {
            Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally{
            try{
                if(fos!=null)
                    fos.close();
            }
            catch(IOException ex){
                Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

}
