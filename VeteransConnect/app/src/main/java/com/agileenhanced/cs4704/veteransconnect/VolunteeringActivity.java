package com.agileenhanced.cs4704.veteransconnect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class VolunteeringActivity extends AppCompatActivity {

    private Button search;

    private EditText location;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteering);

        search = (Button) findViewById(R.id.button_Search_Volunteering);
        location = (EditText) findViewById(R.id.inputBox_Volunteering_Location);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                
                //Filter By Location
                if (location.getText().toString().isEmpty() == false) {
                    String location_String = location.getText().toString();
                    //Check for location field in DB

                }

                //Pass in list to display
                startActivity(new Intent(getApplicationContext(), VolunteeringResultsActivity.class));
            }
        });


    }
}
