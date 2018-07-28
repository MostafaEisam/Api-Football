package com.example.mostafaeisam.apifootball.services;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mostafaeisam.apifootball.services.RequestListener;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class ServiceFactory {

    public static void getData(Context context, String url, final RequestListener listener)
    {
/*
        //Call Volley
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       listener.onSuccess(response);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onFailure(error.networkResponse.statusCode);
            }
        });
        queue.add(stringRequest);
        */
        Ion.with(context)
                .load(url)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<com.koushikdutta.ion.Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, com.koushikdutta.ion.Response<String> result) {
                        if (result==null || result.getHeaders().code()!=200){
                            if (result==null)
                                listener.onFailure(1);
                            else
                                listener.onFailure(result.getHeaders().code());
                        }else{
                            listener.onSuccess(result.getResult());
                        }
                    }
                });
    }
}
