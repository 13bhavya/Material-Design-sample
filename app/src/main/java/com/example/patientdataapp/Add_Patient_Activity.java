package com.example.patientdataapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import javax.net.ssl.HttpsURLConnection;

public class Add_Patient_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "Add_Patient_Activity";

    public String Firstname, Lastname, Add, Gende, Birth, DepartDropDown, relDoctor;
    EditText FirstName, LastName, Address, RelDoctor;

    public RadioButton PatientGender;
    public RadioGroup PatientGrp;

    private int mYear;
    private int mMonth;
    private int mDay;
    private TextView mDateDisplay;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    static final int DATE_DIALOG_ID = 0;

    private Spinner DropDownDepartment;

    final private String strURLTest = "https://patient-data-management.herokuapp.com/patients";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        FirstName = findViewById(R.id.firstName);
        LastName = findViewById(R.id.lastName);
        Address = findViewById(R.id.PatientAddr);
        PatientGrp = findViewById(R.id.patientSex);
        mDateDisplay = findViewById(R.id.showMyDate);
        DropDownDepartment = findViewById(R.id.DepartDropDown);
        RelDoctor = findViewById(R.id.patientDoc);

        //DepartDrop.setOnItemClickListener((AdapterView.OnItemClickListener) this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.PatientDepartment, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DropDownDepartment.setAdapter(adapter);
        DropDownDepartment.setOnItemSelectedListener(this);

        mDateDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Add_Patient_Activity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                mDateDisplay.setText(date);
            }
        };

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

        DepartDropDown = adapterView.getItemAtPosition(position).toString();

        Toast.makeText(adapterView.getContext(), "Selected: " + DepartDropDown, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    public void submitPatient(View view) {

        Firstname = FirstName.getText().toString();
        Lastname = LastName.getText().toString();
        Add = Address.getText().toString();
        Birth = mDateDisplay.getText().toString();

        relDoctor = RelDoctor.getText().toString();


        if (Firstname.trim().equals("")) {
            FirstName.setError("First Name must not be Empty");

        } else if (Lastname.trim().equals("")) {
            LastName.setError("Last Name must not be Empty");

        } else if (PatientGrp.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(),"Fill",Toast.LENGTH_LONG).show();
        } else {
            int selectedId = PatientGrp.getCheckedRadioButtonId();
            PatientGender = Add_Patient_Activity.this.findViewById(selectedId);
            Intent intent = new Intent(Add_Patient_Activity.this, PatientListActivity.class);
            new PostPatientTask().execute(strURLTest);
            Toast.makeText(getApplicationContext(), Gende, Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }

    }


    // Post Patients
    private class PostPatientTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("first_name", Firstname);
                jsonObject.put("last_name", Lastname);
                jsonObject.put("address", Add);
                jsonObject.put("sex", PatientGender);
                jsonObject.put("date_of_birth", Birth);
                jsonObject.put("department", DepartDropDown);
                jsonObject.put("doctor", relDoctor);
            } catch (JSONException e) {
                Log.d(TAG, e.getLocalizedMessage());
            }

            //String result = Patient.postPatient(jsonObject);
            return Patient.postPatient(jsonObject);

        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(Add_Patient_Activity.this, result, Toast.LENGTH_LONG).show();
        }
    }


}
