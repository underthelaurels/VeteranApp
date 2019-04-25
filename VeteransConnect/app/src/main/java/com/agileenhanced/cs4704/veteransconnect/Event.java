package com.agileenhanced.cs4704.veteransconnect;

public class Event
{
    private String name;
    private String date; // Format must be YYYY-MM-DD
    private String time; // Format must be time in 24-hour format (e.g. 13:30) --- HH:MM
    private String street;
    private String city;
    private String state;
    private int zipcode;

    public Event(String name, String date, String time, String street, String city, String state, int zip)
    {
        this.name = name;
        this.date = date;
        this.time = time;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipcode = zip;
    }

    public String getName()
    {
        return name;
    }

    public String getDate()
    {
        return date;
    }

    public String getTime()
    {
        return time;
    }

    public String getStreet()
    {
        return street;
    }

    public String getCity()
    {
        return city;
    }

    public String getState()
    {
        return state;
    }

    public int getZipcode()
    {
        return zipcode;
    }
}
