package com.agileenhanced.cs4704.veteransconnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

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
        //Toast.makeText(this.getApplicationContext(), "Clicked VetConnect!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), ChatSelection.class));
    }

    public void onClickEmployment(View v)
    {
        //Toast.makeText(this.getApplicationContext(), "Clicked Employment!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), EmploymentActivity.class));
    }

    public void onClickDiscounts(View v)
    {
        //Toast.makeText(this.getApplicationContext(), "Clicked Discounts!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), DiscountActivity.class));
    }

    public void onClickEvents(View v)
    {
        //Toast.makeText(this.getApplicationContext(), "Clicked Events!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), EventsActivity.class));
    }

    public void onClickBenefits(View v)
    {
        Toast.makeText(this.getApplicationContext(), "Clicked Benefits!", Toast.LENGTH_SHORT).show();
    }

    public void onClickStory(View v)
    {
        Toast.makeText(this.getApplicationContext(), "Clicked Story!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed()
    {
        sendLogoutRequest();
        super.onBackPressed();
    }

    // Used to log the user out when they go back from the main screen to the login activity
    private void sendLogoutRequest()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://35.245.223.73/user/logout";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(final String response)
                    {
                        Toast.makeText(getApplicationContext(), "Log-out Successful", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            protected Map<String, String> getParams()
            {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("username", getIntent().getStringExtra("USER_NAME"));
                return MyData;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
