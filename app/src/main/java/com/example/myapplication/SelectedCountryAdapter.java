package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SelectedCountryAdapter extends RecyclerView.Adapter<SelectedCountryAdapter.ViewHolder> {

    private ArrayList<CountryTime> country;

    public SelectedCountryAdapter(ArrayList<CountryTime> cont) {
        country = cont;
    }

    @NonNull
    @Override
    public SelectedCountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View contactView = inflater.inflate(R.layout.country_item, parent, false);
        return new SelectedCountryAdapter.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedCountryAdapter.ViewHolder holder, int position) {
        CountryTime c = country.get(position);

        // Set item views based on your views and data model
        holder.countryName.setText(c.getCountry());
        holder.countryDetails.setText(c.getDetails());
        holder.countryTime.setText(c.getTime());
    }

    @Override
    public int getItemCount() {
        return country.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        public TextView countryName;
        public TextView countryDetails;
        public TextView countryTime;
        public ViewHolder(View itemView) {
            super(itemView);
            countryName = (TextView) itemView.findViewById(R.id.country_name);
            countryDetails = (TextView) itemView.findViewById(R.id.country_details);
            countryTime = (TextView) itemView.findViewById(R.id.time);
            countryName.setOnCreateContextMenuListener(this);
            countryDetails.setOnCreateContextMenuListener(this);;
            countryTime.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem del=menu.add(this.getAdapterPosition(),1,0,"Delete");
            del.setOnMenuItemClickListener(onDelete);
        }
        private final MenuItem.OnMenuItemClickListener onDelete = new MenuItem.OnMenuItemClickListener(){

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==1)
                {
                    Log.d("da","delete called");
                    Log.d("sa",countryName.getText().toString());
                    for(int i=0;i<country.size();i++)
                    {
                        if(country.get(i).getCountry().equalsIgnoreCase(countryName.getText().toString()))
                        {
                            Log.d("s","Found");
                            country=country.get(i).delete();
                            notifyDataSetChanged();
                            break;
                        }

                    }

                }
                else {
                    return false;
                }
                return true;
            }
        };
    }
}
