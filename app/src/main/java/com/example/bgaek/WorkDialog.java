package com.example.bgaek;
import android.app.ProgressDialog;

public class WorkDialog {
    ProgressDialog progressDialog;

    public WorkDialog(ProgressDialog progressDialog){
        this.progressDialog = progressDialog;
    }

    public void showDialog() {
        progressDialog.setMessage("Загрузка");
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
