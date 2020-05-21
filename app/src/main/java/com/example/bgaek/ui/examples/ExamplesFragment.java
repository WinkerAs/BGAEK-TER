package com.example.bgaek.ui.examples;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.bgaek.ChoiceTopicActivity;
import com.example.bgaek.R;
import com.example.bgaek.RecyclerViewAdapterCustom;
import com.example.bgaek.WorkDialog;
import com.example.bgaek.ui.compendium.СompendiumFragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ExamplesFragment extends Fragment implements RecyclerViewAdapterCustom.OnNoteListenner{
    RecyclerView recyclerViewCompendium;
    ArrayList<String> mAuthors = new ArrayList<>();
    ArrayList<String> mIdAuthors = new ArrayList<>();

    ProgressDialog progressDialog;
    WorkDialog workDialog;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_examples, container, false);

        recyclerViewCompendium = root.findViewById(R.id.recyclerViewExamples);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        workDialog = new WorkDialog(progressDialog);
        workDialog.showDialog();

        initRecyclerView();

        return root;
    }
    public void initRecyclerView() {
        MyTask myTask = new MyTask();
        myTask.execute();
    }

    HashMap<Integer, String> hashMap;

    @Override
    public void onNoteClick(int postition) {
        Log.d("onClick",">"+postition);
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {

        String name, url;//Тут храним значение заголовка сайта

        @Override
        protected Void doInBackground(Void... params) {

            Document doc2 = null;//Здесь хранится будет разобранный html документ

            try {

                doc2 = Jsoup.connect("https://bgaek.000webhostapp.com/getDataExample.php").get();
                name = doc2.select("b").text();
                url = doc2.select("h2").text();

            } catch (IOException e) {
                //Если не получилось считать
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            String[] masTitle = name.split(";");
            String[] masIdAuthor = url.split(";");
            hashMap = new HashMap<Integer, String>();

            for (int i = 0; i < masTitle.length; i++){
                mAuthors.add(masTitle[i]);
                mIdAuthors.add(masIdAuthor[i]);
                hashMap.put(i, masIdAuthor[i]);
            }

            RecyclerViewAdapterCustom adapterAuthors = new RecyclerViewAdapterCustom(getActivity(), mAuthors, new RecyclerViewAdapterCustom.OnNoteListenner() {
                @Override
                public void onNoteClick(int postition) {
                    Intent intent = new Intent(getActivity(), ChoiceTopicActivity.class);
                    intent.putExtra("URL_PDF", hashMap.get(postition));
                    startActivity(intent);
                }
            });
            recyclerViewCompendium.setAdapter(adapterAuthors);
            recyclerViewCompendium.setLayoutManager(new LinearLayoutManager(getActivity()));

            LayoutAnimationController controller = AnimationUtils
                    .loadLayoutAnimation(getActivity(), R.anim.list_layout_controller);
            recyclerViewCompendium.setLayoutAnimation(controller);
            workDialog.hideDialog();
        }
    }
}