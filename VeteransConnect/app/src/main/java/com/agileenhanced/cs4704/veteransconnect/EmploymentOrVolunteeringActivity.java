package com.agileenhanced.cs4704.veteransconnect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class EmploymentOrVolunteeringActivity extends AppCompatActivity {

    private Button employment;
    private Button volunteering;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employment_or_volunteering);

        employment = (Button) findViewById(R.id.button_Employment);
        volunteering = (Button) findViewById(R.id.button_Volunteering);

        employment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EmploymentActivity.class));
            }
         });

        volunteering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), VolunteeringActivity.class));
            }
        });
    }
}
