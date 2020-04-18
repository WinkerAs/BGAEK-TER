package com.example.bgaek.ui.tests_list;

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
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.bgaek.PracticeActivity;
import com.example.bgaek.R;
import com.example.bgaek.RecyclerViewAdapterTest;
import com.example.bgaek.TestActivity;
import com.example.bgaek.WorkDialog;
import com.example.bgaek.ui.notConnect.FragmentNotConnect;
import com.example.bgaek.ui.practice.PracticeFragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TestsListFragment extends Fragment implements RecyclerViewAdapterTest.OnNoteListenner{//Тесты на прохождение

    RecyclerView recyclerViewTest;

    ArrayList<String> mTitle = new ArrayList<>();
    ArrayList<String> mIdTest = new ArrayList<>();
    ArrayList<String> mImages = new ArrayList<>();
    HashMap<Integer, String> hashMap = new HashMap<>();
    String idStudentText;
    ProgressDialog progressDialog;
    WorkDialog workDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tests, container, false);

        //        idStudentText = getActivity().getIntent().getExtras().getString("id_student");
        idStudentText = "2";
        ConnectivityManager connManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileNetwork = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        recyclerViewTest = root.findViewById(R.id.recyclerViewTest);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        workDialog = new WorkDialog(progressDialog);
        workDialog.showDialog();

        if (mWifi.isConnected() || mobileNetwork.isConnected()){
            initRecyclerView();
        }else{
            Bundle bundle = new Bundle();
            bundle.putInt("message", 3);
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

            String[] masTitle = {"Тест 1", "Тест 2", "Тест 3", "Тест 4"};
            String[] masIdCategories = {"1", "2", "3", "4"};

            for (int i = 0; i < masTitle.length; i++){
                mTitle.add(masTitle[i]);
                mImages.add("https://im0-tub-by.yandex.net/i?id=b9427bbc7b833840385f7b1ab57905d3-l&n=13");
                mIdTest.add(masIdCategories[i]);
            }

            RecyclerViewAdapterTest adapterCategories = new RecyclerViewAdapterTest(getActivity(), mTitle, mImages, new RecyclerViewAdapterTest.OnNoteListenner() {
                @Override
                public void onNoteClick(int postition) {
                    Intent intent = new Intent(getActivity(), TestActivity.class);
                    intent.putExtra("idTest", mIdTest.get(postition));
                    intent.putExtra("id_student", idStudentText);
                    startActivity(intent);
                }
            });
            recyclerViewTest.setAdapter(adapterCategories);
            recyclerViewTest.setLayoutManager(new LinearLayoutManager(getActivity()));

            LayoutAnimationController controller = AnimationUtils
                    .loadLayoutAnimation(getActivity(), R.anim.list_layout_controller);
            recyclerViewTest.setLayoutAnimation(controller);
            workDialog.hideDialog();
        }
    }


}