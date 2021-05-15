package com.jobcall.jobcall.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jobcall.jobcall.activity.ExternalLoginActivity;

import java.util.Optional;

public class API  {

    private static final String url = "http://jofre/";

    public static void loginWithGithub(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);

        String end = "github";
        final Optional<String>[] authURI = new Optional[]{Optional.empty()};
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + end, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("GET", "Response is " + response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("GET", "Error " + error);
                    }
                });
        queue.add(stringRequest);
    }

    public static void loginWithStack(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);

        String end = "stack";
        final Optional<String>[] authURI = new Optional[]{Optional.empty()};
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + end, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("GET", "Response is " + response);
                Intent stack = new Intent(context, ExternalLoginActivity.class);
                stack.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                stack.putExtra("from", "login");
                stack.putExtra("url", response);
                stack.setAction("STACK");
                context.startActivity(stack);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("GET", "Error " + error);
                        return;
                    }
                });
        queue.add(stringRequest);

    }

}
