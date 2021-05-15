package com.jobcall.jobcall.service;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Optional;

public class API {

    private final RequestQueue queue;
    private final String url ="http://jofre/";

    public API(Context context) {
         queue = Volley.newRequestQueue(context);
    }

    public Optional<Intent> loginWithGithub() {
        String end = "stack";
        final Optional<String>[] authURI = new Optional[]{Optional.empty()};
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> authURI[0] = Optional.of(response), error -> {

        });
        return authURI[0].map(uri -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(uri));
            return (Intent) i;
        });
    }
}
