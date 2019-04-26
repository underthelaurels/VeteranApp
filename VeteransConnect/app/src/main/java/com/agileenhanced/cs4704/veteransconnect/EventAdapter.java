package com.agileenhanced.cs4704.veteransconnect;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EventAdapter extends BaseAdapter
{
    private Context context;
    ArrayList<Event> events = new ArrayList<Event>();

    public EventAdapter(Context context)
    {
        this.context = context;
    }

    public void addEvent(Event event)
    {
        this.events.add(event);
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return events.size();
    }

    @Override
    public Object getItem(int i)
    {
        return events.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        EventViewHolder holder = new EventViewHolder();
        LayoutInflater eventInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Event event = events.get(i);

        view = eventInflater.inflate(R.layout.event_layout, null);
        holder.name = view.findViewById(R.id.event_name);
        holder.dateTime = view.findViewById(R.id.event_datetime);
        holder.location = view.findViewById(R.id.event_location);
        view.setTag(holder);

        holder.name.setText(event.getName());
        holder.dateTime.setText("Date: " + event.getDate() + ", Time: " + event.getTime());
        holder.location.setText(event.getStreet() + "\n" + event.getCity() + ", " + event.getState() + " " + event.getZipcode());
        return view;
    }
}
 class EventViewHolder
 {
     public TextView name;
     public TextView dateTime;
     public TextView location;
 }