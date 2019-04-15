package com.agileenhanced.cs4704.veteransconnect;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EmploymentActivity extends AppCompatActivity {


    private Button search;

    private FloatingActionButton resume;

    private EditText jobTitle;
    private EditText location;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employment);

        search = (Button) findViewById(R.id.button_Search_Jobs);
        resume = (FloatingActionButton) findViewById(R.id.floatingActionButton_Resume);

        jobTitle = (EditText) findViewById(R.id.inputBox_Job_Title);
        location = (EditText) findViewById(R.id.inputBox_Job_Location);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Filter By Location
                if (location.getText().toString().isEmpty() == false) {
                    String location_String = location.getText().toString();
                    //Check for location field in DB

                }

                if (jobTitle.getText().toString().isEmpty() == false) {
                    //Filter for job title
                }

                //Pass in list to display
                startActivity(new Intent(getApplicationContext(), EmploymentResultsActivity.class));
            }
        });

        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //Upload Resume
            }
        });
    }
}
