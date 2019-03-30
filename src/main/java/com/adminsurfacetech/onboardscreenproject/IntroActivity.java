package com.adminsurfacetech.onboardscreenproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Animatable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabIndicator;
    Button btnNext;
    int position = 0 ;
    Button btnGetStarted;
    TextView skiptext;
    Animation btnAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // make the activity on full screen

         requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

            // check if the screen layout has been opened once
        if (restorePrefData()){
            Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainActivity);
            finish();
        }


        setContentView(R.layout.activity_intro);
        // hide action bar
        getSupportActionBar().hide();
        // int views
        btnNext = findViewById(R.id.btn_next);
        tabIndicator = findViewById(R.id.tab_indicator);
        btnGetStarted = findViewById(R.id.btn_get_started);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);
        skiptext = findViewById(R.id.Skip_txt);

        //fill the list
        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("FRESH FOOD","learn it is a good place for all student in the world.. learn study and compete,,learn it is a good place for all student in the world.. learn study and compete",R.drawable.img1));
        mList.add(new ScreenItem("FAST FOOD","learn it is a good place for all student in the world.. study and challege other users,,learn it is a good place for all student in the world.. learn study and compete",R.drawable.img2));
        mList.add(new ScreenItem("EASY FOOD","its the best of all you cant afford to loose the opportunity of been a users on learn it,,learn it is a good place for all student in the world.. learn study and compete",R.drawable.img3));

        // slides screen setter
        screenPager = findViewById(R.id.screen_viewPager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this, mList);
        screenPager.setAdapter(introViewPagerAdapter);

        //set up tablayout

        tabIndicator.setupWithViewPager(screenPager);

        // next button click listerner

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = screenPager.getCurrentItem();
                        if(position < mList.size()){

                            position++;
                            screenPager.setCurrentItem(position);


                        }
                        if (position == mList.size()-1){// when it get to the last screen view

                            //TODO : show the GETSTARTED  Button and hide the next button

                            loadLastScreen();

                        }


            }
        });
        skiptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLastScreen();
            }
        });

        // tabchange listerner

        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == mList.size()-1){
                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //Get started onclick listener...
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open the real activity

                Intent mainActivity = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(mainActivity);
                // save the content so it wont keep appearing each time a user check on it

                savePrefsData();
                finish();
            }
        });
    }

    private boolean restorePrefData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        Boolean isIntroActivityOpenedBefore =  pref.getBoolean("isIntroOpened", false);
        return isIntroActivityOpenedBefore;
    }

    private void savePrefsData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened", true);
        editor.commit();
    }

    // show the GETSTARTED  Button and hide the next button...
    private void loadLastScreen() {
        skiptext.setVisibility(View.INVISIBLE);
        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);

        //TODO: Add an animation get started button
        // create the animation button
        btnGetStarted.setAnimation(btnAnim);
    }
}
