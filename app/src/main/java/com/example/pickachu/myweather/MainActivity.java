package com.example.pickachu.myweather;

import android.content.pm.ActivityInfo;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private boolean Ind=false;
    private ImageView layoutBg;
    private String BaseUrl,FinalUrl,LastUrl,CityName;
    private EditText city;
    private TextView Temp,result;
    private String []Img={"clear","clouds","haze","mist","rain","smoke","drizzle"};

    @Override
    public boolean onSupportNavigateUp(){

        finish();
        return true;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        BaseUrl = "http://api.openweathermap.org/data/2.5/weather?q=";
        CityName = "Ranchi";
        LastUrl = "&appid=9998a4ce40de1984f09b1074b7c1942d";
        layoutBg = findViewById(R.id.bg);
        Button button = findViewById(R.id.button);
        city = findViewById(R.id.City);
        result = findViewById(R.id.Result);
        Temp= findViewById(R.id.Temp);
        //http://api.openweathermap.org/data/2.5/weather?q=Ranchi&appid=9998a4ce40de1984f09b1074b7c1942d


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(Ind) layoutBg.animate().alpha(0);

                    CityName = city.getText().toString();
                    FinalUrl = BaseUrl + CityName + LastUrl;

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FinalUrl, null,

                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    try {
                                        String weather = response.getString("weather");
                                        JSONArray arr = new JSONArray(weather);
                                        for (int i = 0; i < arr.length(); i++) {

                                            JSONObject jsonObject = arr.getJSONObject(i);
                                            String des=jsonObject.getString("description");
                                            for (String aImg : Img) {
                                                if (des.contains(aImg)) {
                                                    int res = getResources().getIdentifier(aImg, "drawable", getPackageName());

                                                    layoutBg.animate().alpha(1).setDuration(1000);
                                                    layoutBg.setImageResource(res);
                                                    Ind = true;
                                                }
                                            }
                                            result.setText(des);

                                        }
                                        String Main= response.getString("main");
                                        JSONObject jsonObject= new JSONObject(Main);
                                        int Temperature = jsonObject.getInt("temp")-273;
                                        Temp.setText(String.valueOf(Temperature+ " °C"));


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Toast.makeText(getApplicationContext(), "Your Search did't match any city!!!", Toast.LENGTH_SHORT).show();
                                    Ind=false;

                                }
                            }
                    );

                    MySingleton.getInstance(MainActivity.this).add(jsonObjectRequest);


                }
            });


    }
}