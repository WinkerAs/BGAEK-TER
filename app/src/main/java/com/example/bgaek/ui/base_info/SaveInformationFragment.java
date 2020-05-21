package com.example.bgaek.ui.base_info;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.bgaek.ChoiceTopicActivity;
import com.example.bgaek.R;
import com.example.bgaek.RecyclerViewAdapterCustom;
import com.example.bgaek.WorkDialog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

public class SaveInformationFragment extends Fragment implements RecyclerViewAdapterCustom.OnNoteListenner{//Сборник информации загружаемый из БД (В бд хранятся ссылки на файлы и картинки)

    RecyclerView recyclerViewLiterature;
    ArrayList<String> mTitle = new ArrayList<>();
    ArrayList<String> mURL = new ArrayList<>();
    ArrayList<String> mImages = new ArrayList<>();
    ProgressDialog progressDialog;
    WorkDialog workDialog;
    String URL;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_base_info, container, false);
        URL = "https://bgaek.000webhostapp.com/getLiterature.php";
        recyclerViewLiterature = root.findViewById(R.id.recyclerViewLiterature);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        workDialog = new WorkDialog(progressDialog);
        workDialog.showDialog();
        initRecyclerView();
        return root;
    }
    public void initRecyclerView(){
        MyTask myTask = new MyTask();
        myTask.execute();
    }

    @Override
    public void onNoteClick(int postition) {
        Log.d("onClick",">"+postition);
    }

    class MyTask extends AsyncTask<Void, Void, Void> {

        String title, image, URLPDF;//Тут храним значение заголовка сайта

        @Override
        protected Void doInBackground(Void... params) {

            Document doc = null;//Здесь хранится будет разобранный html документ

            try {
                doc = Jsoup.connect(URL).get();

                title = doc.select("h1").text();
                image = doc.select("h2").text();
                URLPDF = doc.select("b").text();

            } catch (IOException e) {
                //Если не получилось считать
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            String[] masTitle = title.split(";");
            String[] masImage = image.split(";");
            String[] masURL = URLPDF.split(";");

            for (int i = 0; i < masTitle.length; i++){
                mTitle.add(masTitle[i]);
                mImages.add(masImage[i]);
                mURL.add(masURL[i]);
            }

            RecyclerViewAdapterCustom adapterCategories = new RecyclerViewAdapterCustom(getActivity(), mTitle, new RecyclerViewAdapterCustom.OnNoteListenner() {
                @Override
                public void onNoteClick(int postition) {
                    Intent intent = new Intent(getActivity(), ChoiceTopicActivity.class);
                    intent.putExtra("URL_PDF", mURL.get(postition));
                    startActivity(intent);
                }
            });
            recyclerViewLiterature.setAdapter(adapterCategories);
            recyclerViewLiterature.setLayoutManager(new LinearLayoutManager(getActivity()));

            LayoutAnimationController controller = AnimationUtils
                    .loadLayoutAnimation(getActivity(), R.anim.list_layout_controller);
            recyclerViewLiterature.setLayoutAnimation(controller);
            workDialog.hideDialog();
        }
    }
}