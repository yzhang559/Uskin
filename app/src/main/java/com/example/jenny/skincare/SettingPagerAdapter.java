package com.example.jenny.skincare;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Jenny on 6/22/16.
 */
public class SettingPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public SettingPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                SettingTab1 userTab1 = new SettingTab1();
                return userTab1;
            case 1:
                SettingTab2 userTab2 = new SettingTab2();
                return userTab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
