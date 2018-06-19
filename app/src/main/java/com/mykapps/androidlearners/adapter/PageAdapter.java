package com.mykapps.androidlearners.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mykapps.androidlearners.fragment.FavoritesFragment;
import com.mykapps.androidlearners.fragment.JavaProgrammingFragment;
import com.mykapps.androidlearners.fragment.KotlinProgrammingFragment;

public class PageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public PageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        switch (position) {
            case 0:
                return new JavaProgrammingFragment();
            case 1:
                return new KotlinProgrammingFragment();
            case 2:
                return new FavoritesFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return numOfTabs;
    }
}

