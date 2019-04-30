package com.agileenhanced.cs4704.veteransconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class PostJobActivity extends AppCompatActivity
{
    private EditText title;
    private EditText description;
    private EditText industry;
    private EditText dueDate;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);

        title = (EditText) findViewById(R.id.input_job_title);
        description = (EditText) findViewById(R.id.input_job_description);
        industry = (EditText) findViewById(R.id.input_job_industry);
        dueDate = (EditText) findViewById(R.id.input_job_due_date);
    }

    public void onClickPost(View view)
    {
        if (validate())
        {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://35.245.223.73/employment/add-job";

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(final String response)
                        {
                            if (response.contains("\"status\":\"success\""))
                            {
                                Toast.makeText(getApplicationContext(), "I WORKED", Toast.LENGTH_SHORT).show();
                                onSubmitSuccess();
                            } else
                            {
                                Toast.makeText(getApplicationContext(), "Failed to post job", Toast.LENGTH_SHORT).show();
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
                    MyData.put("title", title.getText().toString());
                    MyData.put("description", description.getText().toString());
                    MyData.put("industry", industry.getText().toString());
                    MyData.put("dueDate", dueDate.getText().toString());
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
        String jTitle = title.getText().toString();
        String jDescription = description.getText().toString();
        String jIndustry = industry.getText().toString();
        String jDueDate = dueDate.getText().toString();

        if (jTitle.isEmpty())
        {
            valid = false;
            title.setError("Enter a valid job title");
        }
        if (jDueDate.isEmpty())
        // Add more error checking for date eventually
        {
            valid = false;
            dueDate.setError("Enter a properly formatted date");
        }
        if (jDescription.isEmpty())
        {
            valid = false;
            description.setError("Enter a valid description");
        }
        if (jIndustry.isEmpty())
        {
            valid = false;
            industry.setError("Enter a valid industry");
        }

        return valid;
    }

    private void onSubmitSuccess()
    {
        Toast.makeText(getApplicationContext(), "Posted Job", Toast.LENGTH_SHORT).show();
        this.finish();
    }
}