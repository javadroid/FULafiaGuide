package com.leedroids.fulafiaguide;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.Timer;
import java.util.TimerTask;

import Adaptors.SliderAdapter;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager ;
    int[] images;
    private CardView history,faculties,principalofficers, onlineChannels,map,more,pastQuestion;
    private ScrollView scrollView;
    private BottomNavigationView bottomNavigationView;
    private boolean isFragShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        history = findViewById(R.id.History);
        faculties = findViewById(R.id.Faculties);
        principalofficers = findViewById(R.id.PrincipalOfficer);
        onlineChannels = findViewById(R.id.onlineChannels);
        map = findViewById(R.id.Map);
        more = findViewById(R.id.More);
        pastQuestion = findViewById(R.id.past_question);
        scrollView = findViewById(R.id.scrollView);

        bottomNavigationView = findViewById(R.id.bottomBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new NavigationListener(this));

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),History.class);
                startActivity(intent);
            }
        });

       faculties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent facultyIntent = new Intent(getApplicationContext(),Faculties.class);
               startActivity(facultyIntent);
            }
        });

        principalofficers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent principalIntent = new Intent(getApplicationContext(),PrincipalOfficers.class);
                startActivity(principalIntent);
            }
        });

        onlineChannels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ocIntent = new Intent(getApplicationContext(),OnlineChannelsActivity.class);
                startActivity(ocIntent);
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapIntent= new Intent(getApplicationContext(),FulafiaMapActivity.class);
                startActivity(mapIntent);
            }
        });
        pastQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pqIntent = new Intent(getApplicationContext(),PastQuestionActivity.class);
                startActivity(pqIntent);
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                More fragment = new More();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction
                        .replace(R.id.mFragment, fragment, "More")
                        .commit();
                isFragShowing = true;
            }
        });

        // reference to silder images, stored in an array
        images = new int[] {
                R.drawable.schgate,
                R.drawable.library,
                R.drawable.central,
                R.drawable.health,
                R.drawable.hostel,
                R.drawable.senate,
                R.drawable.mbrs,
                R.drawable.adamu,
                R.drawable.gatenight
        };

        viewPager.setAdapter(new SliderAdapter(this, images));
        TabLayout sliderDots = findViewById(R.id.slideDots);
        sliderDots.setupWithViewPager(viewPager);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(),1000,10000);
    }

    @Override
    public void onBackPressed() {
        if(isFragShowing){
            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.mFragment)).commit();
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                this.finishAffinity();
            }else{
                this.finish();
            }
        }
    }

    private class  SliderTimer extends TimerTask {
        @Override
        public  void  run(){
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem()< images.length - 1){
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    }else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }
}
