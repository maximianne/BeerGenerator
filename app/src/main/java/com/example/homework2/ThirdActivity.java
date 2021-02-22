package com.example.homework2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ThirdActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Beer> beers;
    private ArrayList<Beer> beersFiltered;
    private boolean switch_response;
    private String search;
    private String dateFrom;
    private String dateTo;
    private int size;
    TextView resultAmount;

    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Results");
        resultAmount=findViewById(R.id.textView_tnum);
        //beersFiltered=new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_activity);
        recyclerView=findViewById(R.id.recyclerView_Beers);
        Intent intent=getIntent();
        beers= intent.getExtras().getParcelableArrayList("beers");
        search=intent.getStringExtra("search");
        dateFrom=intent.getStringExtra("dateFrom");
        dateTo=intent.getStringExtra("dateTo");
        switch_response=intent.getExtras().getBoolean("switch");
        //add the filter list
        Log.d("double:", beers.get(1).getBAC());
        Log.d("씨발", beers.get(1).getFoodPairings().toString());
        Log.d("switch", String.valueOf(switch_response));
        beersFiltered=filterOut(beers);



        size=beersFiltered.size();

        beerAdapter adapter = new beerAdapter(beersFiltered);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultAmount=findViewById(R.id.textView_tnum);
        if (size == 0) {
            resultAmount.setText("Sorry there were no results for your search.");
        }
        else{
            resultAmount.setText("There were " + size + " results for your search");
        }
    }

    public ArrayList<Beer> filterOut(ArrayList<Beer> list){
        ArrayList<Beer> cleaned = new ArrayList<>();
        double highPointLev=5.0;
        //if search is empty, or they didn't fill in both of the dates
        if((search.equals("")&& dateTo.equals("")&&dateFrom.equals("")&& switch_response==false) ||
                (search.equals("")&& !dateTo.equals("")&&dateFrom.equals("")&& switch_response==false)||
                (search.equals("")&& dateTo.equals("")&& !dateFrom.equals("")&& switch_response==false)){
           for(int i=0;i<list.size()-1;i++){
               cleaned.add(list.get(i));
            }
        }
        //if search isn't empty
        if(!search.equals("")&& dateTo.equals("")&&dateFrom.equals("")&& switch_response==false){
            for(int i=0;i<list.size()-1;i++){
                if(list.get(i).getBeerName().contains(search)){
                    cleaned.add(list.get(i));
                }
            }
//
        }
        //if search and high point isn't empty
        if(!search.equals("")&& dateTo.equals("")&&dateFrom.equals("")&& switch_response==true){
            for(int i=0;i<list.size()-1;i++){
                if(list.get(i).getBeerName().contains(search)
                        && Double.parseDouble(list.get(i).getBAC())>=highPointLev){
                    cleaned.add(list.get(i));
                }
            }
        }
        //if high point is checked
        if(search.equals("")&& dateTo.equals("")&&dateFrom.equals("")&& switch_response==true){
            for(int i=0;i<list.size()-1;i++){
                double t=Double.valueOf(list.get(i).getBAC());
                if(t>=highPointLev){
                    cleaned.add(list.get(i));
                }
            }
        }
        //date to and date from
        if(search.equals("")&& !dateTo.equals("")&& !dateFrom.equals("")&& switch_response==false){
            //00/0000
            //0,1,x,3,4,5,6,
            int date1=Integer.parseInt(dateFrom.substring(3,6));
            int monMin=Integer.parseInt(dateFrom.substring(0,1));
            int date2=Integer.parseInt(dateTo.substring(3,6));
            int time=date2-date1;
            for(int i=0;i<list.size()-1;i++){
                int date3= Integer.parseInt(list.get(i).getFirstBrewed().substring(3,6));
                int monI=Integer.parseInt(list.get(i).getFirstBrewed().substring(0,1));
                if(date3+time<=date2){
                    if(monMin<=monI){
                        cleaned.add(list.get(i));
                    }
                }
            }
        }

        //if search and date
        if(!search.equals("")&& !dateTo.equals("")&& !dateFrom.equals("")&& switch_response==false){
            //00/0000
            //0,1,x,3,4,5,6,
            int date1=Integer.parseInt(dateFrom.substring(3,6));
            int monMin=Integer.parseInt(dateFrom.substring(0,1));
            int date2=Integer.parseInt(dateTo.substring(3,6));
            int time=date2-date1;
            for(int i=0;i<list.size()-1;i++){
                int date3= Integer.parseInt(list.get(i).getFirstBrewed().substring(3,6));
                int monI=Integer.parseInt(list.get(i).getFirstBrewed().substring(0,1));
                if(date3+time<=date2){
                    if(monMin<=monI && list.get(i).getBeerName().contains(search)){
                        cleaned.add(list.get(i));
                    }
                }
            }
        }

        //if date and high point
        if(search.equals("")&& !dateTo.equals("")&& !dateFrom.equals("")&& switch_response==true){
            //00/0000
            //0,1,x,3,4,5,6,
            int date1=Integer.parseInt(dateFrom.substring(3,6));
            int monMin=Integer.parseInt(dateFrom.substring(0,1));
            int date2=Integer.parseInt(dateTo.substring(3,6));
            int time=date2-date1;

            for(int i=0;i<list.size()-1;i++){
                int date3= Integer.parseInt(list.get(i).getFirstBrewed().substring(3,6));
                int monI=Integer.parseInt(list.get(i).getFirstBrewed().substring(0,1));
                double t=Double.valueOf(list.get(i).getBAC());
                if(date3+time<=date2){
                    if(monMin<=monI && t>=highPointLev){
                        cleaned.add(list.get(i));
                    }
                }
            }
        }
        //all of them
        if(!search.equals("")&& !dateTo.equals("")&& !dateFrom.equals("")&& switch_response==true){
            //00/0000
            //0,1,x,3,4,5,6,
            int date1=Integer.parseInt(dateFrom.substring(3,6));
            int monMin=Integer.parseInt(dateFrom.substring(0,1));
            int date2=Integer.parseInt(dateTo.substring(3,6));
            int time=date2-date1;

            for(int i=0;i<list.size()-1;i++){
                int date3= Integer.parseInt(list.get(i).getFirstBrewed().substring(3,6));
                int monI=Integer.parseInt(list.get(i).getFirstBrewed().substring(0,1));
                double t=Double.valueOf(list.get(i).getBAC());
                if(date3+time<=date2){
                    if(monMin<=monI && t>=highPointLev && list.get(i).getBeerName().contains(search)){
                        cleaned.add(list.get(i));
                    }
                }
            }
        }
        return cleaned;
    }

}
