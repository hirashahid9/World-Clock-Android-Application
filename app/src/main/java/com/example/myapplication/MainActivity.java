package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ArrayList<CountryTime> countries;
    RecyclerView rvCountries;
    CountryDAOClass dao;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao=new CountryDAOClass(this);
        rvCountries = (RecyclerView) findViewById(R.id.rvCountries);
        rvCountries.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Country> c=new ArrayList<>();
        countries= CountryTime.createCountryList(c.size(),c,dao);
        rvCountries.setAdapter((new SelectedCountryAdapter(countries)));
  }

    public void onSaveInstanceState(Bundle outState)
    {
        outState.putParcelableArrayList("countrylist",  countries);
        super.onSaveInstanceState(outState);
    }
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        countries= (ArrayList<CountryTime>) savedInstanceState.get("countrylist");
        rvCountries = (RecyclerView) findViewById(R.id.rvCountries);
        rvCountries.setLayoutManager(new LinearLayoutManager(this));
        rvCountries.setAdapter((new SelectedCountryAdapter(countries)));
    }

    public void onClickButton(View View)
    {
            //Button b=(Button)View;
            Intent clicked = new Intent(this,SecondActivity.class);
            clicked.putExtra("selCountry",countries);
            startActivityForResult(clicked,1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1)
        {
            if(resultCode==RESULT_OK)
            {
                dao=new CountryDAOClass(this);
                countries.removeAll(countries);
                ArrayList<Country> newCoun=(ArrayList<Country>)data.getSerializableExtra("selectedCountries");
                if(newCoun!=null) {
                    ArrayList<CountryTime> newC=CountryTime.createCountryList(newCoun.size(),newCoun,dao);
                    for (int i = 0; i < newC.size(); i++) {
                        if (!countries.contains(newC.get(i))) {
                            countries.add(newC.get(i));
                        }
                    }

                }

                rvCountries = (RecyclerView) findViewById(R.id.rvCountries);
                rvCountries.setLayoutManager(new LinearLayoutManager(this));
                rvCountries.setAdapter((new SelectedCountryAdapter(countries)));
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.storage_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
               CountryTime c=new CountryTime(dao);
               c.save(countries);
               Toast.makeText(this,"Successfully Saved items.",Toast.LENGTH_LONG).show();
               return true;
            case R.id.load:
                countries=CountryTime.load(dao);
                rvCountries = (RecyclerView) findViewById(R.id.rvCountries);
                rvCountries.setLayoutManager(new LinearLayoutManager(this));
                rvCountries.setAdapter((new SelectedCountryAdapter(countries)));
                Toast.makeText(this,"Successfully loaded saved items.",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
