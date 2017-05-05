package com.example.mayankaggarwal.nasago;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StateActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_state);
        editText=(EditText)findViewById(R.id.state);
        button=(Button)findViewById(R.id.ok);

//        getSupportActionBar().setTitle("Region");
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=editText.getText().toString().toLowerCase();

                if(text.equals("")){
                    Toast.makeText(StateActivity.this,"Enter State",Toast.LENGTH_SHORT).show();
                }else {
                    Geocoder coder = new Geocoder(StateActivity.this);
                    try {
                        ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(text, 50);
                        for(Address add : adresses){
                            if (true) {
                                double longitude = add.getLongitude();
                                double latitude = add.getLatitude();

                                Globals.latdouble=latitude;
//                                Log.d("tagg",latitude+" "+longitude);

                                String population = new String(getPopulation(text));
                                String value = new String(getValue(text));

                                Globals.population=new String(population);
                                Globals.value=new String(value);

//                                Log.d("tagg",population+" "+value);

                                Intent intent=new Intent(StateActivity.this,ChartActivity.class);
                                startActivity(intent);

                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    private String getPopulation(String text) {
        JsonParser jsonParser=new JsonParser();

        JsonObject popJson=jsonParser.parse(Globals.popJSON).getAsJsonObject();
        JsonArray cityArray=popJson.get("data").getAsJsonArray();

//        Log.d("tagg",cityArray.toString());

        for(int i=0 ; i<cityArray.size() ; i++)
        {
            JsonObject cityJsonObject = cityArray.get(i).getAsJsonObject();

            String City = cityJsonObject.get("City").getAsString();

            if(City.toLowerCase().contains(text.toLowerCase()))
            {
                return cityJsonObject.get("population").getAsString();
            }
        }

        return "5,49,391";
    }

    private String getValue(String text) {
        JsonParser jsonParser=new JsonParser();

        JsonObject popJson=jsonParser.parse(Globals.popJSON).getAsJsonObject();
        JsonArray cityArray=popJson.get("data").getAsJsonArray();

//        Log.d("tagg",cityArray.toString());

        for(int i=0 ; i<cityArray.size() ; i++)
        {
            JsonObject cityJsonObject = cityArray.get(i).getAsJsonObject();

            String City = cityJsonObject.get("City").getAsString();

            if(City.toLowerCase().contains(text.toLowerCase()))
            {
                return cityJsonObject.get("value").getAsString();
            }
        }

        return "0.38800152";
    }
}
