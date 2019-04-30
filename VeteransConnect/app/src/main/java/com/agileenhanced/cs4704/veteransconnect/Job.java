package com.agileenhanced.cs4704.veteransconnect;

import android.widget.EditText;

public class Job
{
    private String title;
    private String description; // Format must be YYYY-MM-DD
    private String industry; // Format must be time in 24-hour format (e.g. 13:30) --- HH:MM
    private String dueDate;

    public Job(String title, String description, String industry, String dueDate)
    {
        this.title = title;
        this.description = description;
        this.industry = industry;
        this.dueDate = dueDate;
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
}
