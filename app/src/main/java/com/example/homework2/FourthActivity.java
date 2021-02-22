package com.example.homework2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class FourthActivity extends AppCompatActivity {
    private Beer beer;
    private TextView textView_title;
    private TextView textView_BAC;
    private TextView textView_firstBrewed;
    private ImageView imageView_beerPic;
    private TextView textView_des;
    private TextView textView_foodPairing;
    private TextView textView_tips;
    private String  str_foodpairings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourth_activity);
        Intent intent=getIntent();
        beer = intent.getExtras().getParcelable("beer");
        str_foodpairings="";
        textView_title=findViewById(R.id.textView_4title);
        textView_title.setText(beer.getBeerName());

        textView_BAC=findViewById(R.id.textView_4ABV);
        textView_BAC.setText("BAC: " + beer.getBAC() + "%");

        textView_des=findViewById(R.id.textView_4description);
        textView_des.setText("Description: " + beer.getBeerDescription());

        textView_firstBrewed=findViewById(R.id.textView_firstBrewed);
        textView_firstBrewed.setText("First brewed: " + beer.getFirstBrewed());

        textView_foodPairing=findViewById(R.id.textView_4foodPairing);
        Log.d("array", beer.getFoodPairings().toString());
        textView_foodPairing.setText("Recommended Food Pairings: " +beer.getFoodPairings());

        textView_tips = findViewById(R.id.textView_4Tips);
        textView_tips.setText("Brewer Tips: " + beer.getBrewerTips());

        imageView_beerPic=findViewById(R.id.imageView_4beerPic);
        Picasso.get().load(beer.getImage_url()).into(imageView_beerPic);
    }

}
