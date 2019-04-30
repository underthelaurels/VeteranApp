package com.agileenhanced.cs4704.veteransconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class PostEventActivity extends AppCompatActivity
{
    private EditText name;
    private EditText date;
    private EditText time;
    private EditText street;
    private EditText city;
    private EditText state;
    private EditText zipcode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_event);

        name = (EditText) findViewById(R.id.input_event_name);
        date = (EditText) findViewById(R.id.input_event_date);
        time = (EditText) findViewById(R.id.input_event_time);
        street = (EditText) findViewById(R.id.input_event_street);
        city = (EditText) findViewById(R.id.input_event_city);
        state = (EditText) findViewById(R.id.input_event_state);
        zipcode = (EditText) findViewById(R.id.input_event_zipcode);
    }

    public void onClickSubmit(View view)
    {
        if (validate())
        {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://35.245.223.73/service/add-event";

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(final String response)
                        {
                            if (response.contains("\"status\":\"success\""))
                            {
                                onSubmitSuccess();
                            } else
                            {
                                Toast.makeText(getApplicationContext(), "Failed to post event", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
            })
            {
                protected Map<String, String> getParams()
                {
                    Map<String, String> MyData = new HashMap<String, String>();
                    MyData.put("name", name.getText().toString());
                    MyData.put("date", date.getText().toString());
                    MyData.put("time", time.getText().toString());
                    MyData.put("street_address", street.getText().toString());
                    MyData.put("city", city.getText().toString());
                    MyData.put("state", state.getText().toString());
                    MyData.put("zipcode", zipcode.getText().toString());
                    return MyData;
                }
            };

            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
    }

    private boolean validate()
    {
        boolean valid = true;
        String eName = name.getText().toString();
        String eDate = date.getText().toString();
        String eTime = time.getText().toString();
        String eStreet = street.getText().toString();
        String eCity = city.getText().toString();
        String eState = state.getText().toString();
        String eZip = zipcode.getText().toString();

        if (eName.isEmpty())
        {
            valid = false;
            name.setError("Enter a valid event name");
        }
        if (eDate.length() != 10 && !eDate.substring(4, 5).equals("-") && !eDate.substring(7, 8).equals("-"))
            // Add more error checking for date eventually
        {
            valid = false;
            date.setError("Enter a properly formatted date");
        }
        if (eTime.length() != 5 && !eTime.substring(2, 3).equals(":"))
        {
            valid = false;
            time.setError("Enter a valid time");
        }
        if (eStreet.isEmpty())
        {
            valid = false;
            street.setError("Enter a valid street address");
        }
        if (eCity.isEmpty())
        {
            valid = false;
            city.setError("Enter a valid city name");
        }
        if (eState.length() != 2)
        {
            valid = false;
            state.setError("Enter a valid 2-letter state abbreviation");
        }
        if (eZip.length() != 5)
        {
            valid = false;
            zipcode.setError("Enter a valid 5-digit zipcode");
        }

        return valid;
    }

    private void onSubmitSuccess()
    {
        Toast.makeText(getApplicationContext(), "Posted Event", Toast.LENGTH_SHORT).show();
        this.finish();
    }
}
