package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.ArrayList;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> implements Filterable {


    private ArrayList<Country> country;
    private ArrayList<Country> selected=null;
    private ArrayList<Country> filteredCountries;
    private Filter filter;

    public CountryAdapter(ArrayList<Country> cont, OnClickListener onClick) {
        country = cont;
        filteredCountries=cont;
        this.onClickListener=onClick;
        filter=null;
    }

    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new CountryFilter();
        }
        return filter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView countryName;
        public CheckBox cb;
        public TextView countryTime;
        public ViewHolder(View itemView) {
            super(itemView);
            countryName = (TextView) itemView.findViewById(R.id.country_name1);
            countryTime = (TextView) itemView.findViewById(R.id.time1);
            cb =(CheckBox) itemView.findViewById(R.id.checkBox);
        }

    }

    public CountryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View contactView = inflater.inflate(R.layout.all_country_list, parent, false);
        return new ViewHolder(contactView);
    }
    private OnClickListener onClickListener;

    @Override
    public void onBindViewHolder(CountryAdapter.ViewHolder holder, int position) {
        Country c = filteredCountries.get(position);
        //country.get(position);

        // Set item views based on your views and data model
        holder.countryName.setText(c.getCountry());
        holder.countryTime.setText(c.getTime());
        holder.cb.setChecked(c.isUsed());
        holder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.cb.isChecked()) {
                    onClickListener.onItemClick(c);
                } else {
                    onClickListener.onItemUnClick(c);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return filteredCountries.size();//country.size();
    }

    private class CountryFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults result=new FilterResults();
            if(constraint!=null && constraint.length()>0)
            {
                Log.d("new","Inside");
                ArrayList<Country> filteredCountry=new ArrayList<>();
                for (int i=0;i< country.size();i++)
                {
                    if(country.get(i).getCountry().toLowerCase().contains(constraint.toString().toLowerCase()))
                    {
                        filteredCountry.add(country.get(i));
                    }
                }
                result.count=filteredCountry.size();
                result.values=filteredCountry;
            }
            else
            {
                result.values=country;
                result.count=country.size();
            }
            return result;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredCountries= (ArrayList<Country>) results.values;
            notifyDataSetChanged();
        }
    }

}
