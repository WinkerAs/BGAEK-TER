package com.example.bgaek.ui.practice;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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

import com.example.bgaek.PracticeActivity;
import com.example.bgaek.R;
import com.example.bgaek.RecyclerViewAdapterTest;
import com.example.bgaek.WorkDialog;
import com.example.bgaek.ui.notConnect.FragmentNotConnect;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PracticeFragment extends Fragment implements RecyclerViewAdapterTest.OnNoteListenner {//Список учителей загружаемых с базы данных

    RecyclerView recyclerViewPractice;

    ArrayList<String> mTitle = new ArrayList<>();
    ArrayList<String> mIdPractice = new ArrayList<>();
    ArrayList<String> mImages = new ArrayList<>();
    HashMap<Integer, String> hashMap = new HashMap<>();
    String idStudentText;
    ProgressDialog progressDialog;
    WorkDialog workDialog;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_practice, container, false);

//        idStudentText = getActivity().getIntent().getExtras().getString("id_student");
        idStudentText = "2";
        ConnectivityManager connManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileNetwork = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        recyclerViewPractice = root.findViewById(R.id.recyclerViewPractice);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        workDialog = new WorkDialog(progressDialog);
        workDialog.showDialog();

        if (mWifi.isConnected() || mobileNetwork.isConnected()){
            initRecyclerView();
        }else{
            Bundle bundle = new Bundle();
            bundle.putInt("message", 2);
            FragmentNotConnect fragmentNotConnect = new FragmentNotConnect();
            fragmentNotConnect.setArguments(bundle);

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, fragmentNotConnect)
                    .commit();
            workDialog.hideDialog();
        }

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

        String title,idPractice;//Тут храним значение заголовка сайта

        @Override
        protected Void doInBackground(Void... params) {

            Document doc = null;//Здесь хранится будет разобранный html документ

            try {
                //Считываем заглавную страницу http://harrix.org
                doc = Jsoup.connect("https://versewin.000webhostapp.com/getCategories.php").get();

                title = doc.select("b").text();
                idPractice = doc.select("li").text();

            } catch (IOException e) {
                //Если не получилось считать
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //String[] masTitle = title.split(";");
            //String[] masIdCategories = idPractice.split(";");

            String[] masTitle = {"Практика 1", "Практика 2", "Практика 3", "Практика 4"};
            String[] masIdCategories = {"1", "2", "3", "4"};

            for (int i = 0; i < masTitle.length; i++){
                    mTitle.add(masTitle[i]);
                    mImages.add("https://stavka-bk.ru/wp-content/uploads/2020/03/teoriya-stavok.png");
                    mIdPractice.add(masIdCategories[i]);
            }

            RecyclerViewAdapterTest adapterCategories = new RecyclerViewAdapterTest(getActivity(), mTitle, mImages, new RecyclerViewAdapterTest.OnNoteListenner() {
                @Override
                public void onNoteClick(int postition) {
                    Intent intent = new Intent(getActivity(), PracticeActivity.class);
                    intent.putExtra("idPractice", mIdPractice.get(postition));
                    intent.putExtra("id_student", idStudentText);
                    startActivity(intent);
                }
            });
            recyclerViewPractice.setAdapter(adapterCategories);
            recyclerViewPractice.setLayoutManager(new LinearLayoutManager(getActivity()));

            LayoutAnimationController controller = AnimationUtils
                    .loadLayoutAnimation(getActivity(), R.anim.list_layout_controller);
            recyclerViewPractice.setLayoutAnimation(controller);
            workDialog.hideDialog();
        }
}}