package com.agileenhanced.cs4704.veteransconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickVetConnect(View v)
    {
        Toast.makeText(this.getApplicationContext(), "Clicked VetConnect!", Toast.LENGTH_SHORT).show();
    }

    public void onClickEmployment(View v)
    {
        Toast.makeText(this.getApplicationContext(), "Clicked Employment!", Toast.LENGTH_SHORT).show();
    }

    public void onClickDiscounts(View v)
    {
        Toast.makeText(this.getApplicationContext(), "Clicked Discounts!", Toast.LENGTH_SHORT).show();
    }

    public void onClickEvents(View v)
    {
        Toast.makeText(this.getApplicationContext(), "Clicked Events!", Toast.LENGTH_SHORT).show();
    }

    // TODO: onBackPressed() might need to logout unless the user states they want to be remembered
}
