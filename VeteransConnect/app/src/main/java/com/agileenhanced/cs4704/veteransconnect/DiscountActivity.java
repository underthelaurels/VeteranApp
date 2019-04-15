package com.agileenhanced.cs4704.veteransconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class DiscountActivity extends AppCompatActivity {

    private CheckBox auto;
    private CheckBox clothes;
    private CheckBox food;
    private CheckBox misc;
    private CheckBox outdoors;
    private CheckBox vacation;

    private EditText nameSearch;
    private EditText location;

    private Button search;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discounts);

        auto = (CheckBox)findViewById(R.id.checkBox_Auto);
        clothes = (CheckBox)findViewById(R.id.checkBox_Clothes);
        food = (CheckBox)findViewById(R.id.checkBox_Food);
        misc = (CheckBox)findViewById(R.id.checkBox_Misc);
        outdoors = (CheckBox)findViewById(R.id.checkBox_Outdoors);
        vacation = (CheckBox)findViewById(R.id.checkBox_Vacation);

        nameSearch = (EditText) findViewById(R.id.inputBox_Search_By_Name);
        location = (EditText) findViewById(R.id.inputBox_Location);

        search = (Button) findViewById(R.id.button_Search_Discounts);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){


                //Filter By Location
                if (location.getText().toString().isEmpty() == false) {
                    String location_String = location.getText().toString();
                    //Check for location field in DB

                }

                if (auto.isChecked()){
                    //Filter for auto category in DB
                } else if (clothes.isChecked()) {
                    //Filter for clothes category in DB
                } else if (food.isChecked()) {
                    //Filter for food category in DB
                } else if (misc.isChecked()) {
                    //Filter for misc category in DB
                } else if (outdoors.isChecked()) {
                    //Filter for outdoors category in DB
                } else if (vacation.isChecked()) {
                    //Filter for vacation category in DB
                }

                if (nameSearch.getText().toString().isEmpty() == false)
                {
                    //Filter for Name
                }

                //Pass in list to display
                startActivity(new Intent(getApplicationContext(), DiscountResultsActivity.class));
            }
        });

    }


}
