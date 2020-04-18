package com.example.bgaek.ui.compendium;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.bgaek.ChoiceCompendiumActivity;
import com.example.bgaek.R;
import com.example.bgaek.RecyclerViewAdapterCustom;
import com.example.bgaek.WorkDialog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class СompendiumFragment extends Fragment implements RecyclerViewAdapterCustom.OnNoteListenner{

    RecyclerView recyclerViewCompendium;
    ArrayList<String> mAuthors = new ArrayList<>();
    ArrayList<String> mIdAuthors = new ArrayList<>();

    ProgressDialog progressDialog;
    WorkDialog workDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_compendium, container, false);

        recyclerViewCompendium = root.findViewById(R.id.recyclerViewCompendium);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        workDialog = new WorkDialog(progressDialog);
        workDialog.showDialog();

        initRecyclerView();

        return root;
    }

    public void initRecyclerView() {

        String[] masTitle = {"Элементы комбинаторики", "Случайные события", "Вероятность", "Теоремы сложения и умножения вероятностей", "Повторные испытания", "Случайные велечины", "Законы распределения СВ"};
        String[] masIdAuthor = {"1", "2", "3", "4", "5", "6", "7"};

        for (int i = 0; i < masTitle.length; i++) {
                mAuthors.add(masTitle[i]);
                mIdAuthors.add(masIdAuthor[i]);
        }

        Collections.reverse(mIdAuthors);
        Collections.reverse(mAuthors);

        RecyclerViewAdapterCustom adapterAuthors = new RecyclerViewAdapterCustom(getActivity(), mAuthors, new RecyclerViewAdapterCustom.OnNoteListenner() {
            @Override
            public void onNoteClick(int postition) {
                Intent intent = new Intent(getActivity(), ChoiceCompendiumActivity.class);

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

    @Override
    public void onNoteClick(int postition) {
        Log.d("onClick", ">" + postition);
    }

}