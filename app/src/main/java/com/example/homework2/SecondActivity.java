package com.example.homework2;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SecondActivity extends AppCompatActivity {

    private EditText editText_search;
    private EditText editText_datefrom;
    private EditText editText_dateto;
    ImageView imageView;
    Button next;
    private static final String api_url="https://api.punkapi.com/v2/beers";
    private static AsyncHttpClient client = new AsyncHttpClient();
    private ArrayList<Beer> beers;
    private Switch swtich_highPoint; //4.0 ABV or above
    private boolean switch_response;

    private String search;
    private String dateFrom;
    private String dateTo;

    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Let's find your drink");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        editText_search=findViewById(R.id.texteditSearch);
        editText_search.setHint("Search by name...");
        editText_datefrom=findViewById(R.id.texteditdate);
        editText_datefrom.setHint("MM/YYYY");
        editText_dateto=findViewById(R.id.texteditdate2);
        editText_dateto.setHint("MM/YYYY");
        imageView=findViewById(R.id.imageView2);
        next= findViewById(R.id.button_nextAct);
        imageView.setImageBitmap(ImageViaAssets("groupDrinks.jpeg"));
        swtich_highPoint=findViewById(R.id.switch1);
        swtich_highPoint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switch_response=true;
                } else {
                       switch_response=false;
                }
            }
        });
        beers= new ArrayList<>();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchNextActivity(v);
            }
        });

    }

    public Bitmap ImageViaAssets(String fileName){
        AssetManager assetmanager = getAssets();
        InputStream is = null;
        try{
            is = assetmanager.open(fileName);
        }catch(IOException e){
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    }
    public void launchNextActivity(View view){
        client.addHeader("accept", "application/json");
        client.get(api_url, new AsyncHttpResponseHandler() {
            @Override
            /**All beer endpoints return a json array with a number of beer objects inside.
             */
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String imageStatic_url= "https://pbs.twimg.com/media/DfcIVx3W0AAzHl5?format=jpg&name=360x360";
                String imageSelect_url="https://pbs.twimg.com/media/Dfa_0-RX4AI45kG?format=jpg&name=360x360";
                try {
                    JSONArray beerArray= new JSONArray((new String(responseBody)));
                    Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                    for(int i=0; i<beerArray.length();i++){
                        JSONObject beerObject= beerArray.getJSONObject(i);

                        JSONArray arrayTemp = beerObject.getJSONArray("food_pairing");
                        ArrayList<String> temp2= new ArrayList<>();
                        for(int j=0; j<arrayTemp.length()-1;j++){
                            String a=arrayTemp.getString(j);
                            Log.d("asdf",a);
                            temp2.add(a);
                        }

                        Log.d("array",temp2.toString());
                        Beer beer = new Beer(beerObject.getString("name"),
                                beerObject.getString("description"),
                                beerObject.getString("abv"),
                                beerObject.getString("first_brewed"),
                                beerObject.getString("brewers_tips"),
                                temp2,
                                beerObject.getString("image_url"),
                                imageStatic_url,
                                imageSelect_url
                                );
                        beers.add(beer);
                    }
                    search=editText_search.getText().toString();
//                    Log.d("serach", search);
//                    Log.d("first",beers.get(0).getBeerName());
                    dateFrom = editText_datefrom.getText().toString();
                    dateTo=editText_dateto.getText().toString();
                    intent.putExtra("beers", beers);
                    intent.putExtra("search", search);
                    intent.putExtra("dateFrom", dateFrom);
                    intent.putExtra("dateTo", dateTo);
                    intent.putExtra("switch", switch_response);
                //  Log.d("switch",String.valueOf(switch_response));
                    startActivity(intent);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("api error", new String(responseBody));
            }
        });
    }
}
