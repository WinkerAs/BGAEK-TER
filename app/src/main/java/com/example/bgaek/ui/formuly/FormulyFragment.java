package com.example.bgaek.ui.formuly;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.bgaek.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FormulyFragment extends Fragment {

    PDFView pdfViewFormulae;
    String urlPDF;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_formuly, container, false);
        urlPDF = "https://bgaek.000webhostapp.com/Formuly.pdf";
        pdfViewFormulae = root.findViewById(R.id.pdfFormulae);

        new RetrievePDFStream(pdfViewFormulae).execute(urlPDF);
        return root;
    }

    class RetrievePDFStream extends AsyncTask<String, Void, InputStream>
    {
        PDFView pdfView;
        public RetrievePDFStream(PDFView pdfView) {
            this.pdfView = pdfView;
        }

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                if (urlConnection.getResponseCode() == 200){
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            }catch (IOException e){
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).load();
        }
    }
}