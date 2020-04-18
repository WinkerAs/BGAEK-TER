package com.example.bgaek.ui.notConnect;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bgaek.R;
import com.example.bgaek.ui.practice.PracticeFragment;
import com.example.bgaek.ui.tests_list.TestsListFragment;


public class FragmentNotConnect extends Fragment {

    Button buttonRefreshWifiFragment;
    TextView textViewNotConnectFragment;
    String TAG = "FragmentNotConnect";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_not_connect, container, false);

        buttonRefreshWifiFragment= root.findViewById(R.id.buttonRefreshWifiFragment);
        textViewNotConnectFragment = root.findViewById(R.id.textViewNotConnectFragment);
        textViewNotConnectFragment.setText("Проверьте Ваши настройки доступа\nк интернету или попробуйте\nзайти позже");

        final Integer strtext =getArguments().getInt("message");

        buttonRefreshWifiFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                switch (strtext) {
                    case 1:

                        break;
                    case 2:
                        fragmentManager.beginTransaction()
                                .replace(R.id.nav_host_fragment, new PracticeFragment())
                                .commit();
                        break;
                    case 3:
                        fragmentManager.beginTransaction()
                                .replace(R.id.nav_host_fragment, new TestsListFragment())
                                .commit();
                        break;
                    case 4:

                        break;
                }
            }
        });

        return root;
    }
}