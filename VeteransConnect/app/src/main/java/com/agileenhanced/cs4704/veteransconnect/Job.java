package com.agileenhanced.cs4704.veteransconnect;

import android.widget.EditText;

public class Job
{
    private String title;
    private String description; // Format must be YYYY-MM-DD
    private String industry; // Format must be time in 24-hour format (e.g. 13:30) --- HH:MM
    private String dueDate;
    private String address;
    private String city; // Format must be YYYY-MM-DD
    private String state; // Format must be time in 24-hour format (e.g. 13:30) --- HH:MM
    private String zip;

    public Job(String title, String description, String industry, String dueDate,
               String address, String city, String state, String zip)
    {
        this.title = title;
        this.description = description;
        this.industry = industry;
        this.dueDate = dueDate;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }

    public String getIndustry()
    {
        return industry;
    }

    public String getDueDate()
    {
        return dueDate;
    }

    public String getAddress()
    {
        return address;
    }

    public String getCity()
    {
        return city;
    }

    public String getState()
    {
        return state;
    }

    public String getZip()
    {
        return zip;
    }
}
