package com.example.bgaek;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.bgaek.ui.answer_practice.AnswerPracticeFragment;
import com.example.bgaek.ui.main.SectionsPagerAdapter;
import com.example.bgaek.ui.task_practice.TaskPracticeFragment;

public class PracticeActivity extends AppCompatActivity {

    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabs = findViewById(R.id.tabs);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.AddFragment(new TaskPracticeFragment(), "Задания");
        viewPagerAdapter.AddFragment(new AnswerPracticeFragment(), "Ответы");

        viewPager.setAdapter(viewPagerAdapter);
        tabs.setupWithViewPager(viewPager);
        tabs.getTabAt(0).setIcon(R.drawable.question);
        tabs.getTabAt(1).setIcon(R.drawable.answer);
    }
}