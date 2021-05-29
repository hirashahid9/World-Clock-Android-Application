package com.example.myapplication;

import java.util.ArrayList;
import java.util.Hashtable;

public interface CountryDAO {
    public void save(Hashtable<String,String> attributes);
    public void save(ArrayList<Hashtable<String,String>> objects);
    public ArrayList<Hashtable<String,String>> load();
    public Hashtable<String,String> load(String id);
    public void deleteAll();
    public void delete(String attribute);
}
