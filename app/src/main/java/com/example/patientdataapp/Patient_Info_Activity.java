package com.example.patientdataapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

public class Patient_Info_Activity extends AppCompatActivity {

    TextView viewName, viewAddr, viewSex, viewDOB, viewDepart, viewDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_patient__info_);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewName = findViewById(R.id.viewFirst);
        viewAddr = findViewById(R.id.viewAddress);
        viewSex = findViewById(R.id.viewSex);
        viewDOB = findViewById(R.id.viewBirth);
        viewDepart = findViewById(R.id.viewDepartment);
        viewDoctor = findViewById(R.id.viewRelateDoctor);
    }
}
