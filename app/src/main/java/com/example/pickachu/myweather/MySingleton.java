package com.example.pickachu.myweather;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Pickachu on 15-02-2018.
 */

public class MySingleton {

    private  static MySingleton instance;
    private Context mcontext;
    private  static RequestQueue requestQueue;

    private MySingleton(Context context){

        mcontext=context;
        requestQueue=getRequestqueue(mcontext.getApplicationContext());
    }

    public RequestQueue getRequestqueue(Context context) {

        if (requestQueue == null) {

            requestQueue = Volley.newRequestQueue(context);

        }
        return requestQueue;
    }

    public static synchronized MySingleton getInstance(Context context) {

        if (instance == null) {

            instance = new MySingleton(context);

        }
        return instance;
    }



    public void add(Request request){
        requestQueue.add(request);

    }


}
