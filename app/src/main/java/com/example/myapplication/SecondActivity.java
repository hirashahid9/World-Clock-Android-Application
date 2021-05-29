package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.*;
import java.util.Calendar;

public class SecondActivity extends AppCompatActivity implements OnClickListener {
    ArrayList<Country> countries;
    ArrayList<Country> selected=null;
    ArrayList<CountryTime> select= null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        RecyclerView rvCountries = (RecyclerView) findViewById(R.id.rvCountries);
        rvCountries.setLayoutManager(new LinearLayoutManager(this));

        String[] list = TimeZone.getAvailableIDs();
        ArrayList<String> str = new ArrayList<>();

        Intent intent = getIntent();
        selected = new ArrayList<>();
        select = (ArrayList<CountryTime>) intent.getSerializableExtra("selCountry");

            for (int i = 0; i < list.length; i++) {
                str.add(list[i]);
            }

            for (int i = 0; select != null && i < select.size(); i++) {
                if (str.contains(select.get(i).getCountry())) {
                    str.remove(select.get(i).getCountry());
                }
            }

            countries = Country.createCountryList(str.size(), str);
            for (int i = 0; select != null && i < select.size(); i++) {
                Country c1 = select.get(i).getCountryandTime();
                c1.setUsed(true);
                c1.updateDateTime();
                if (!countries.contains(c1)) {
                    countries.add(c1);
                }
                if (!select.contains(c1)) {
                    selected.add(c1);
                }
            }
        Collections.sort(countries);
        CountryAdapter cA=new CountryAdapter(countries,this);
        rvCountries.setAdapter(cA);
        CheckBox cb= (CheckBox) findViewById(R.id.checkBox);
        EditText searchTo = (EditText)findViewById(R.id.editText);
        searchTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                cA.getFilter().filter(s.toString());
                Log.d("as","After Text Changed");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("as",s.toString());
                cA.getFilter().filter(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("as","Text Changed");
                cA.getFilter().filter(s.toString());
            }
        });
    }

    public void onSaveInstanceState(Bundle outState)
    {
        Log.d("pdr","inside saf");
        Log.d("prdd",countries.get(0).getCountry());
        outState.putParcelableArrayList("countrylist",  selected);
        super.onSaveInstanceState(outState);
    }
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        Log.d("per","inside res");
        super.onRestoreInstanceState(savedInstanceState);
        selected= (ArrayList<Country>) savedInstanceState.get("countrylist");


        for (int i = 0; selected != null && i < selected.size(); i++) {
            for (int j=0;j<countries.size();j++)
            {
                if(countries.get(j).getCountry().equalsIgnoreCase(selected.get(i).getCountry()))
                {
                    countries.get(j).setUsed(true);
                }
            }
        }
        Collections.sort(countries);

    }

    public void onItemClick(Country c)
    {
        if (selected == null) {
            selected = new ArrayList<>();
        }
        if(!selected.contains(c)) {
            selected.add(c);
            c.setUsed(true);
            Toast.makeText(getApplicationContext(),c.getCountry()+" added",Toast.LENGTH_SHORT).show();
        }

    }

    public void onItemUnClick(Country c)
    {
        if (selected != null) {
            for(int i=0;i<selected.size();i++)
            {
                if(selected.get(i).getCountry().equalsIgnoreCase(c.getCountry()))
                {
                    selected.get(i).setUsed(false);
                    selected.remove(selected.get(i));
                }
            }
            c.setUsed(false);
            Toast.makeText(getApplicationContext(),c.getCountry()+" removed",Toast.LENGTH_SHORT).show();
        }
    }

    public void onBackPressed()
    {
        Intent intent=new Intent();
        intent.putExtra("selectedCountries",selected);
//        Log.d("myTag",selected.get(0).getCountry());
        setResult(RESULT_OK,intent);
        super.onBackPressed();
    }
}
