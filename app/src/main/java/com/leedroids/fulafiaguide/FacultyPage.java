package com.leedroids.fulafiaguide;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Adaptors.DepartmentAdapter;
import model.DepartmentModel;

public class FacultyPage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<DepartmentModel> departmentModel;
    private DepartmentAdapter departmentAdapter;
    private ImageView facultyPhoto;
    private String f_name;
    private int f_image;
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    CollapsingToolbarLayout collapsingToolbarLayout;

    private String[] facultyOfArt = {"English","French","History","Visual and Creative Arts","Philosophy","Theater and Media Arts","Christian Religion Studies","Islamic Studies","Hausa Language","Arabic Studies"};
    private String[] facultyOofComputing = {"Computer Science","Software Engineering","Information Technology","Information Systems","Cyber Security"};
    private String[] facultyOfScience = {"Mathematics","Physics","Chemistry","Microbiology","Biochemistry","Statistics","Geography","Geology","Zoology","Science Laboratory Technology"};
    private String[] facultyOfSocialScience = {"Economics","Political Science","Sociology","Social Work","Mass Communications","Psychology","Business Administration","Accounting"};
    private String[] facultyOfAgriculture = {"Agronomy","Agricultural Economics and Extension Services","Agriculture and Fisheries","Forestry and Wildlife Management"};
    private String[] facultyOfEducation = {"Computer Science Education","Mathematics Education","Physics Education","Biology Education","Chemistry Education","Library and Information Science","Special Needs and Rehabilitation Education","Business Education","Integrated Science Education"};
    private String[] facultyOfManagementScience = {"Accounting","Business Administration"};
    private String[] collegeOfMedicine = {"Medical Laboratory Science","Anatomy","Physiology","Medicine","Nursing","Radiography"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_page);

        //Reference to all widgets
        facultyPhoto = findViewById(R.id.facultyImage);
        recyclerView = findViewById(R.id.faculty_recycler_view);

        Bundle extras = getIntent().getExtras();
        f_image = extras.getInt("facultyImage");
        f_name = extras.getString("facultyName");

        facultyPhoto.setImageResource(f_image);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        collapsingToolbarLayout = findViewById(R.id.ctb);
        collapsingToolbarLayout.setTitle("Departments In "+f_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        departmentModel = new ArrayList<DepartmentModel>();

        switch(f_name){
            case "Faculty of Arts":
                showDepartments(facultyOfArt);
                break;
            case "Faculty of Computing":
                showDepartments(facultyOofComputing);
                break;
            case "Faculty of Science":
                showDepartments(facultyOfScience);
                break;
            case "Faculty of Social Sciences":
                showDepartments(facultyOfSocialScience);
                break;
            case "Faculty of Education":
                showDepartments(facultyOfEducation);
                break;
            case "Faculty of Management Science":
                showDepartments(facultyOfManagementScience);
                break;
            case "Faculty of Agriculture":
                showDepartments(facultyOfAgriculture);
                break;
            case "College of Medicine":
                showDepartments(collegeOfMedicine);
                break;
        }

        departmentAdapter = new DepartmentAdapter(FacultyPage.this, departmentModel);
        recyclerView.setAdapter(departmentAdapter);

        bottomNavigationView = findViewById(R.id.bottomBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new NavigationListener(this));

    }

    private void showDepartments(String[] arr) {
        Arrays.sort(arr);
        for (int i = 0; i < arr.length; i++) {
            DepartmentModel item = new DepartmentModel(arr[i]);
            departmentModel.add(item);
        }
    }

}

