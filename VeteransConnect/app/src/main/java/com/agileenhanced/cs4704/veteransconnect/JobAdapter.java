package com.agileenhanced.cs4704.veteransconnect;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class JobAdapter extends BaseAdapter
{
    private Context context;
    ArrayList<Job> jobs = new ArrayList<Job>();

    public JobAdapter(Context context)
    {
        this.context = context;
    }

    public void addJob(Job job)
    {
        this.jobs.add(job);
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return jobs.size();
    }

    @Override
    public Object getItem(int i)
    {
        return jobs.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        JobViewHolder holder = new JobViewHolder();
        LayoutInflater jobInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Job job = jobs.get(i);

        view = jobInflater.inflate(R.layout.job_layout, null);
        holder.title = view.findViewById(R.id.job_title);
        holder.dateIndustry = view.findViewById(R.id.job_dateIndustry);
        holder.description = view.findViewById(R.id.job_description);
        view.setTag(holder);

        holder.title.setText(job.getTitle());
        holder.dateIndustry.setText("Date: " + job.getDueDate() + ", Industry: " + job.getIndustry());
        holder.description.setText(job.getDescription());
        return view;
    }
}
class JobViewHolder
{
    public TextView title;
    public TextView dateIndustry;
    public TextView description;
}