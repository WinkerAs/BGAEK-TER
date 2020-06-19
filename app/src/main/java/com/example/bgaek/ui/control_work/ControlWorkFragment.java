package com.example.bgaek.ui.control_work;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.bgaek.ControlActivity;
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

public class ControlWorkFragment extends Fragment{

    private RecyclerView recyclerViewControlWork;

    private ArrayList<String> mTitle = new ArrayList<>();
    private ArrayList<String> mIdPractice = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private String idStudentText;
    ProgressDialog progressDialog;
    WorkDialog workDialog;
    private static final String url = "https://bgaek.000webhostapp.com/getControlWork.php";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_control_work, container, false);

        idStudentText = getActivity().getIntent().getExtras().getString("id_student");
        ConnectivityManager connManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileNetwork = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        recyclerViewControlWork = root.findViewById(R.id.recyclerViewControlWork);

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

    class MyTask extends AsyncTask<Void, Void, Void> {

        String title,urlControl;//Тут храним значение заголовка сайта

        @Override
        protected Void doInBackground(Void... params) {

            Document doc = null;//Здесь хранится будет разобранный html документ

            try {
                //Считываем заглавную страницу http://harrix.org
                doc = Jsoup.connect(url).get();

                title = doc.select("b").text();
                urlControl = doc.select("h2").text();

            } catch (IOException e) {
                //Если не получилось считать
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            String[] masTitle = getResources().getStringArray(R.array.Control);
            String[] masUrlPractice = urlControl.split(";");
            for (int i = 0; i < masTitle.length; i++){
                    mTitle.add(masTitle[i]);
                    mImages.add("https://stavka-bk.ru/wp-content/uploads/2020/03/teoriya-stavok.png");
                    mIdPractice.add(masUrlPractice[i]);
            }

            RecyclerViewAdapterTest adapterCategories = new RecyclerViewAdapterTest(getActivity(), mTitle, mImages, new RecyclerViewAdapterTest.OnNoteListenner() {
                @Override
                public void onNoteClick(int postition) {
                    Intent intent = new Intent(getActivity(), PracticeActivity.class);
                    intent.putExtra("urlPractice", mIdPractice.get(postition));
                    intent.putExtra("id_student", idStudentText);
                    intent.putExtra("variant", "2");
                    intent.putExtra("idPractice", String.valueOf(postition));
                    startActivity(intent);
                }
            });
            recyclerViewControlWork.setAdapter(adapterCategories);
            recyclerViewControlWork.setLayoutManager(new LinearLayoutManager(getActivity()));

            LayoutAnimationController controller = AnimationUtils
                    .loadLayoutAnimation(getActivity(), R.anim.list_layout_controller);
            recyclerViewControlWork.setLayoutAnimation(controller);
            workDialog.hideDialog();
        }
}}