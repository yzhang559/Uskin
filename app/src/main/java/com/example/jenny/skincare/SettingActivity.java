package com.example.jenny.skincare;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Jenny on 6/21/16.
 */
public class SettingActivity extends AppCompatActivity{



    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_setting);


            TabLayout userTabLayout = (TabLayout) findViewById(R.id.user_tab_layout);


            userTabLayout.addTab(userTabLayout.newTab().setText("Modify"));
            userTabLayout.addTab(userTabLayout.newTab().setText("Information"));
            userTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            //share info
            final ViewPager userViewPager = (ViewPager) findViewById(R.id.user_pager);
            final SettingPagerAdapter userAdapter = new SettingPagerAdapter(getSupportFragmentManager(), userTabLayout.getTabCount());
            userViewPager.setAdapter(userAdapter);
            userViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(userTabLayout));
            userTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {


                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    userViewPager.setCurrentItem(tab.getPosition());

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });


        }


}
