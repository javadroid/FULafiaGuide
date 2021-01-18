package com.leedroids.fulafiaguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import Adaptors.SearchAdapter;
import model.SearchModel;

public class Faculties extends AppCompatActivity{

    private RecyclerView recyclerView;
    private List<SearchModel> searchModelList;
    private SearchAdapter sAdapter;
    private BottomNavigationView bottomNavigationView;

    private int[] facultyImages = {
            R.drawable.faculty_of_agric,
            R.drawable.facultyofart,
            R.drawable.faculty_of_education,
            R.drawable.facultyofscience,
            R.drawable.faculty_social_science,
            R.drawable.temp
    };
    private String[] facultyNames = {
            "Faculty of Agriculture",
            "Faculty of Arts",
            "Faculty of Education",
            "Faculty of Science",
            "Faculty of Social Sciences",
            "College of Medicine"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculties);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.faculties_recycler_view);

        searchModelList = new ArrayList<SearchModel>();
        for(int i = 0; i < facultyImages.length; i++){
            SearchModel sModel = new SearchModel(facultyImages[i],facultyNames[i]);
            searchModelList.add(sModel);
        }
        sAdapter = new SearchAdapter(this, searchModelList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(sAdapter);

        bottomNavigationView= findViewById(R.id.bottomBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new NavigationListener(this));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                SearchModel beanItem = searchModelList.get(position);
                int FacultyImage = beanItem.getFaculty_Image();
                String FacultyName = beanItem.getFaculty_Name();

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("facultyImage", FacultyImage);
                dataBundle.putString("facultyName", FacultyName);

                // Sends bundle data to FacultyPage for display;
                Intent intent = new
                        Intent(getApplicationContext(), FacultyPage.class);
                intent.putExtras(dataBundle);
                startActivity(intent);


            }

            @Override
            public void onLongClick(View view, int positon) {

            }
        }));
    }
}
