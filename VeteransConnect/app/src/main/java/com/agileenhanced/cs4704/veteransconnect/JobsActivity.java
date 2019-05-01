package com.agileenhanced.cs4704.veteransconnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JobsActivity extends AppCompatActivity
{
    private Button addJob;
    private ListView jobsView;
    private JobAdapter jobAdapter;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);

        addJob = (Button) findViewById(R.id.btn_add_job);
        jobsView = (ListView) findViewById(R.id.jobs_view);
        jobAdapter = new JobAdapter(this);
        jobsView.setAdapter(jobAdapter);
        queue = Volley.newRequestQueue(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        jobAdapter.resetJobs();
        getJobs();
    }

    private void getJobs()
    {
        final String currDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        String url = "http://35.245.223.73/employment/all";

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        // display response
                        try
                        {
                            // Toast.makeText(getApplicationContext(), response.toString(4), Toast.LENGTH_LONG).show();
                            JSONArray jobs = (JSONArray) response.get("jobs");
                            for (int i = 0; i < jobs.length(); i++)
                            {
                                JSONObject currObj = jobs.getJSONObject(i);
                                final Job job = new Job(
                                        currObj.getString("title"),
                                        currObj.getString("description"),
                                        currObj.getString("industry"),
                                        currObj.getString("due_date"),
                                        currObj.getString("street_address"),
                                        currObj.getString("city"),
                                        currObj.getString("state"),
                                        currObj.getString("zipcode"));


                                runOnUiThread(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        jobAdapter.addJob(job);
                                        jobsView.setSelection(0);
                                        //if (compareDates(currDate, job.getDueDate()))
                                        //{
                                        //    jobAdapter.addJob(job);
                                         //   jobsView.setSelection(0);
                                        //}

                                    }
                                });
                            }
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );

        // Add the request to the RequestQueue.
        queue.add(getRequest);
    }

    public void onClickAddJob(View view)
    {
        startActivity(new Intent(getApplicationContext(), PostJobActivity.class));
    }

    public boolean compareDates(String today, String jobDate)
    {
        int t = Integer.parseInt(today.replaceAll("-", ""));
        int ed = Integer.parseInt(jobDate.replaceAll("-", ""));
        return (ed >= t);
    }
}
