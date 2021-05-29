package com.example.myapplication;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import static java.time.LocalDateTime.now;

public class Country implements Serializable,Comparable<Country>, Parcelable {
    private String country;
    private String time;
    private ZonedDateTime dateTime;

    public void setCountry(String country) {
        this.country = country;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private boolean isUsed;

    protected Country(Parcel in) {
        Log.d("pr1","inside cons");
        country = in.readString();
        time = in.readString();
        isUsed = in.readByte() != 0;
        LocalDateTime dt = LocalDateTime.now();
        this.dateTime = dt.atZone(ZoneId.of(country));
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(country));
        time=setTime(this.dateTime,calendar);
        //updateDateTime();
    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            Log.d("pr3","inside country");
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            Log.d("prw", String.valueOf(size));
            return new Country[size];
        }
    };

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public Country(String country) {
        this.country = country;
        this.isUsed=true;
    }

    public Country(String country, String time, ZonedDateTime dt) {
        this.country = country;
        this.time = time;
        this.isUsed=false;
        this.dateTime=dt;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateDateTime() {
        LocalDateTime dt = LocalDateTime.now();
        this.dateTime = dt.atZone(ZoneId.of(country));
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(country));
        time=setTime(this.dateTime,calendar);
    }

 static String setTime(ZonedDateTime dt, Calendar calendar)
 {

     String time="";
     int hour=calendar.get(Calendar.HOUR_OF_DAY);
     int min=calendar.get(Calendar.MINUTE);

     if(calendar.get(Calendar.AM_PM)==1)
     {
         if(hour>12){
             hour=hour-12;
         }
         if(hour<10)
         {
             if(min<10)
             {
                 time="0"+hour+":0"+min+ " PM";
             }
             else
             {
                 time="0"+hour+":"+min+ " PM";
             }
         }
         else
         {
             if(min<10)
             {
              time = hour+":0"+min+ " PM";
             }
             else
             {
                 time = hour+":"+min+ " PM";
             }
         }
     }
     else
     {
         if(hour==0){
             hour=hour+12;
         }
         if(hour<10)
         {
             if(min<10)
             {
                time = "0"+hour+":0"+min+ " AM";
             }
             else
             {
                 time ="0"+hour+":"+min+ " AM";
             }
         }
         else
         {
             if(min<10)
             {
                 time=hour+":0"+min+ " AM";
             }
             else
             {
                 time=hour+":"+min+ " AM";
             }
         }
     }
    return time;
}

    public String getCountry() {
        return country;
    }

    public String getTime() {
        return time;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<Country> createCountryList(int numCountries, ArrayList<String> coun) {

        ArrayList<Country> allCountries = new ArrayList<Country>();

        for (int i = 0; i < numCountries; i++) {

            String time;
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(coun.get(i)));
            LocalDateTime dt = LocalDateTime.now();
            ZonedDateTime fromZonedDateTime = dt.atZone(ZoneId.of(coun.get(i)));

            time=setTime(fromZonedDateTime,calendar);
            allCountries.add(new Country(coun.get(i),time,fromZonedDateTime));
        }

        return allCountries;
    }

    @Override
    public int compareTo(Country o) {
        return country.compareTo(o.country);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Log.d("pr","inside parcel");
        dest.writeString(country);
        dest.writeString(time);
        dest.writeBoolean(isUsed);
    }
}
