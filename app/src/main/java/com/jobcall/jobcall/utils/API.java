package com.jobcall.jobcall.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jobcall.jobcall.activity.ExternalLoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class API {


    public static void loginWithGithub(Context context, String userMail, String uid) {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Constants.SERVER_URL + Constants.GIT_AUTH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("GET", "Response is " + response);
                Intent stack = new Intent(context, ExternalLoginActivity.class);
                stack.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                stack.putExtra("from", "home");
                stack.putExtra("url", response);
                stack.setAction("GIT");
                context.startActivity(stack);
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("GET", "Error " + error);
                    }})
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("mail", userMail);
                    params.put("uid", uid);
                    Log.d("GET", "HEADER mail " + userMail);
                    Log.d("GET", "HEADER id " + uid);

                return params;
            }
        };
        queue.add(stringRequest);
    }

    public static void loginWithStack(Context context, String userMail, String uid) {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Constants.SERVER_URL + Constants.STACK_AUTH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("GET", "Response is " + response);
                        Intent stack = new Intent(context, ExternalLoginActivity.class);
                        stack.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        stack.putExtra("from", "home");
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
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mail", userMail);
                params.put("uid", uid);
                Log.d("GET", "HEADER mail " + userMail);
                Log.d("GET", "HEADER id " + uid);

                return params;
            }
        };
        queue.add(stringRequest);

    }

    public static void unlinkStack(Context context, String userMail, String uid) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Constants.SERVER_URL + Constants.STACK_LOGOUT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        API.retrieveUserData(context, userMail, uid);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("GET", "Error " + error);
                        return;
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mail", userMail);
                params.put("uid", uid);
                Log.d("GET", "HEADER mail " + userMail);
                Log.d("GET", "HEADER id " + uid);

                return params;
            }
        };
        queue.add(stringRequest);
    }

    public static void unlinkGit(Context context, String userMail, String uid) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Constants.SERVER_URL + Constants.GIT_LOGOUT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        API.retrieveUserData(context, userMail, uid);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("GET", "Error " + error);
                        return;
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mail", userMail);
                params.put("uid", uid);
                Log.d("GET", "HEADER mail " + userMail);
                Log.d("GET", "HEADER id " + uid);

                return params;
            }
        };
        queue.add(stringRequest);
    }

    public static void retrieveUserData(Context context, String userMail, String uid) {
        RequestQueue queue = Volley.newRequestQueue(context);

        final Optional<String>[] authURI = new Optional[]{Optional.empty()};
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Constants.SERVER_URL + Constants.USER_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("GET", "Response is " + response);
                        try {
                            JSONObject responseJson = new JSONObject(response);
                            Intent stack = new Intent("USER_DATA");
                            stack.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            stack.putExtra("from", "login");
                            stack.putExtra("github", responseJson.getJSONObject("github").getString("token"));
                            stack.putExtra("stack", responseJson.getJSONObject("stack").getString("token"));
                            context.sendBroadcast(stack);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("GET", "Error " + error);
                        return;
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mail", userMail);
                params.put("uid", uid);
                Log.d("GET", "HEADER mail " + userMail);
                Log.d("GET", "HEADER id " + uid);

                return params;
            }
        };
        queue.add(stringRequest);
    }
}
