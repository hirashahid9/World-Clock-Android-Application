 package com.example.myapplication;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Hashtable;

 public class CountryTime implements Serializable, Parcelable {
    private Country country;
    private String details;
    private CountryDAO dao=null;

    public CountryTime(Country country, String details) {
        this.country = country;
        this.details = details;
    }

    public CountryTime(CountryDAO dao)
    {
        this.dao=dao;
    }

    public CountryTime(Country country, CountryDAO dao) {
        this.country = country;
        this.dao=dao;
    }

    protected CountryTime(Parcel in) {
        details = in.readString();
        country =in.readParcelable(Country.class.getClassLoader());
    }


    public static final Creator<CountryTime> CREATOR = new Creator<CountryTime>() {
        @Override
        public CountryTime createFromParcel(Parcel in) {
            return new CountryTime(in);
        }

        @Override
        public CountryTime[] newArray(int size) {
            return new CountryTime[size];
        }
    };

    public void save(ArrayList<CountryTime> countries){
        Log.d("d","Inside save");

        if (dao != null){

            dao.deleteAll();
            for(int i=0;i<countries.size();i++)
            {
                Hashtable<String,String> data = new Hashtable<String, String>();
                data.put("name",countries.get(i).getCountry());
                dao.save(data);
            }

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<CountryTime> delete(){
        Log.d("d","Inside save");

        if (dao != null){
            dao.delete(country.getCountry());
            return load(dao);
        }
        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void load(Hashtable<String,String> data){
         //details= data.get("details");
        country=new Country(data.get("name"));
         country.updateDateTime();
         country.setUsed(true);
         details=setDetails();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<CountryTime> load(CountryDAO dao){
        ArrayList<CountryTime> countries = new ArrayList<CountryTime>();
        if(dao != null){
            ArrayList<Hashtable<String,String>> objects = dao.load();
            for(Hashtable<String,String> obj : objects){
                CountryTime c1 = new CountryTime(dao);
                c1.load(obj);
                countries.add(c1);
            }
        }
        return countries;
    }

    public Country getCountryandTime() { return country; }

    public String getCountry() {
        return country.getCountry();
    }

    public String getDetails() {
        return details;
    }

    public String getTime() {
        return country.getTime();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String setDetails()
    {
        LocalDateTime dt = LocalDateTime.now();
        ZonedDateTime fromZonedDateTime = country.getDateTime();
        //coun.get(i).getDateTime();
        ZonedDateTime toZonedDateTime = ZonedDateTime.now();
        long diff = Math.abs(Duration.between(fromZonedDateTime, toZonedDateTime).toMillis()/1000);

        if(fromZonedDateTime.isAfter(toZonedDateTime))
        {
            if(diff/60==0)
            {
                return "Same time. ";
            }
            else if(diff<3600){
                String detail=diff/60+" minutes behind.";
                return detail;
            }
            else
            {
                String detail=diff/3600+" hours behind.";
                return detail;
            }
        }
        else
        {
            if(diff/60==0)
            {
                String detail="Same time.";
                return detail;
            }
            else if(diff<3600){
                String detail=diff/60+" minutes ahead.";
                return detail;
            }
            else
            {
                String detail=diff/3600+" hours ahead.";
                return detail;
            //    countries.add(new CountryTime(coun.get(i),diff/3600+" hours ahead."));
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<CountryTime> createCountryList(int numCountries, ArrayList<Country> coun, CountryDAOClass dao) {
        ArrayList<CountryTime> countries = new ArrayList<CountryTime>();

        for (int i = 0; i < numCountries; i++)
        {
            CountryTime c1=new CountryTime(coun.get(i),dao);
            c1.details=c1.setDetails();
            countries.add(c1);
        }
        return countries;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(details);
        dest.writeParcelable(country,flags);
    }
}

