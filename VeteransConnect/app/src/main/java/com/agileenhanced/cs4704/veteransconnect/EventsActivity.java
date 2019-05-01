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

public class EventsActivity extends AppCompatActivity
{
    private Button addEvent;
    private ListView eventsView;
    private EventAdapter eventAdapter;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        addEvent = (Button) findViewById(R.id.btn_add_event);
        eventsView = (ListView) findViewById(R.id.jobs_view);
        eventAdapter = new EventAdapter(this);
        eventsView.setAdapter(eventAdapter);
        queue = Volley.newRequestQueue(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        eventAdapter.resetEvents();
        getEvents();
    }

    private void getEvents()
    {
        final String currDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        String url = "http://35.245.223.73/service/get-event?all=true";
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        // display response
                        try
                        {
                            Toast.makeText(getApplicationContext(), response.toString(4), Toast.LENGTH_LONG).show();
                            JSONArray events = (JSONArray) response.get("events");
                            for (int i = 0; i < events.length(); i++)
                            {
                                JSONObject currObj = events.getJSONObject(i);
                                final Event event = new Event(
                                        currObj.getString("name"),
                                        currObj.getString("date"),
                                        currObj.getString("time"),
                                        currObj.getString("street_address"),
                                        currObj.getString("city"),
                                        currObj.getString("state"),
                                        Integer.parseInt(currObj.getString("zipcode")));
                                runOnUiThread(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        if (compareDates(currDate, event.getDate()))
                                        {
                                            eventAdapter.addEvent(event);
                                            eventsView.setSelection(0);
                                        }

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

    public void onClickAddEvent(View view)
    {
        startActivity(new Intent(getApplicationContext(), PostEventActivity.class));
    }

    public boolean compareDates(String today, String eventDate)
    {
        int t = Integer.parseInt(today.replaceAll("-", ""));
        int ed = Integer.parseInt(eventDate.replaceAll("-", ""));
        return (ed >= t);
    }
}
