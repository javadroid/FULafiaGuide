package com.leedroids.fulafiaguide;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import Adaptors.PrincipalOfficersAdapter;
import model.PrincipalOfficersModel;

public class PrincipalOfficers extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<PrincipalOfficersModel> principalOfficersModels;
    private PrincipalOfficersAdapter principalOfficersAdapter;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    private int[] IMAGE1 = {R.drawable.chancellor,R.drawable.vc,R.drawable.dvc,
		R.drawable.registra,R.drawable.librarian,R.drawable.bursar};
    private String[] NAME = {"Dr. Shekarau Angyu Masa Ibi ","Prof. Shehu Abdur-Rahman","Professor Idris O.O Amali",
		"Mallam Nuruddeen Abdu","Dr Abiodun Iyaro","Mr Daniel Anjola Wilson"};
    private String[] POST = {"Chancellor","Vice Chancellor","Deputy Vice Chancello",
		"Registrar","Librarian","Bursar"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_officers);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager photoLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(photoLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        principalOfficersModels = new ArrayList<PrincipalOfficersModel>();

        for (int i= 0; i< IMAGE1.length; i++){
            PrincipalOfficersModel item = new PrincipalOfficersModel(IMAGE1[i], NAME[i],POST[i]);
            principalOfficersModels.add(item);
        }

        principalOfficersAdapter = new PrincipalOfficersAdapter(PrincipalOfficers.this, principalOfficersModels);
        recyclerView.setAdapter(principalOfficersAdapter);

        bottomNavigationView = findViewById(R.id.bottomBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new NavigationListener(this));
    }
}