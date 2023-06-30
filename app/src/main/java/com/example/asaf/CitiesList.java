package com.example.asaf;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CitiesList {
    private Context context;
    private FireBaseModel fireBaseModel; // Add a reference to the FireBaseModel class

    public CitiesList(Context context) {
        this.context = context;
        fireBaseModel = new FireBaseModel(context); // Initialize the FireBaseModel
    }

    public void fetchCities() {
        String apiUrl = "https://data.gov.il/api/3/action/datastore_search?resource_id=d4901968-dad3-4845-a9b0-a57d027f11ab";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Handle the API response
                        try {
                            JSONObject jsonObject = response.getJSONObject(0);
                            JSONArray results = jsonObject.getJSONArray("שם_ישוב");
                            for (int i = 0; i < results.length(); i++) {
                                String cityName = results.getString(i);
                                // Push the city name to Firebase Realtime Database
                                fireBaseModel.saveCity(cityName);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle API request error
                        error.printStackTrace();
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }
}