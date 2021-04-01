package com.example.csc_330_navigation;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class ServiceClient {
    private static ServiceClient serviceClient;
    private RequestQueue requestQueue;
    private static Context context;
    private String baseUrl = "https://mopsdev.bw.edu/~tmcdowel19/ws.php/notes";

    private ServiceClient(Context ctx) {
        this.context = ctx;
    }

    synchronized public static ServiceClient getInstance() {
        if (serviceClient == null) {
            throw new RuntimeException("Service Client Uninitialized");
        }
        return serviceClient;
    }

    synchronized public static ServiceClient getInstance(Context ctx) {
        if (serviceClient == null) {
            serviceClient = new ServiceClient(ctx);
        }
        return serviceClient;
    }

    synchronized private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    private void addRequest(Request request) {
        this.getRequestQueue().add(request);
    }

    public void get( final Response.Listener<JSONObject> listener, final Response.ErrorListener errorListener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, baseUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorListener.onErrorResponse(error);
            }
        });
        addRequest(jsonObjectRequest);
    }


        //TODO: Make post method
        public void post(Object object, final Response.Listener<JSONObject> listener, final Response.ErrorListener errorListener) {
            Gson gson = new Gson();
            String json = gson.toJson(object);
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject = new JSONObject(json);
            }catch (JSONException e){
               int j = 5;
            }

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, baseUrl, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    listener.onResponse(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    errorListener.onErrorResponse(error);
                }
            });
            addRequest(request);
        }



}

