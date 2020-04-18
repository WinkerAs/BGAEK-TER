package com.example.bgaek;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FragmentCollectionAdapter extends FragmentStatePagerAdapter {
    public FragmentCollectionAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        QuestionFragment questionFragment = new QuestionFragment();
        Bundle bundle = new Bundle();
        i = i + 1;
        bundle.getString("message", "Position: " + i);
        questionFragment.setArguments(bundle);
        return questionFragment;
    }

    @Override
    public int getCount() {
        return 10;
    }
}
