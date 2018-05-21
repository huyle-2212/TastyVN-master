package com.example.huyle.tastyvn;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PageAdapter extends FragmentStatePagerAdapter {

    int NoOfTabs;

    public PageAdapter(FragmentManager fm, int NumberofTabs){
        super(fm);
        this.NoOfTabs = NumberofTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                MainCourse m1 = new MainCourse();
                return m1;
            case 1:
                Favourites f1 = new Favourites();
                return f1;
            case 2:
                Desserts d1 = new Desserts();
                return d1;
            case 3:
                Beverages b1 = new Beverages();
                return b1;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NoOfTabs;
    }
}
