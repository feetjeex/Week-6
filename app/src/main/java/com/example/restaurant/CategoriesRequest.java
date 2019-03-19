package com.example.restaurant;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoriesRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    Context context;
    JSONArray categories;
    Callback activity;


    @Override
    public void onErrorResponse(VolleyError error) {
        String errorMessage = error.getMessage();
        activity.gotCategoriesError("A network error occurred: " + errorMessage);
    }

    @Override
    public void onResponse(JSONObject response) {
        ArrayList<String> arrayList = new ArrayList<String>();
        categories = new JSONArray();
        try {
            categories = response.getJSONArray("categories");
            for (int i = 0; i < categories.length(); i++) {
                arrayList.add((i), categories.getString(i));
            }
        }
        catch (JSONException e) {
        }

        activity.gotCategories(arrayList);
    }

    public interface Callback {
        void gotCategories(ArrayList<String> categories);
        void gotCategoriesError(String message);
    }

    // Write a constructor that accepts a Context type parameter and stores it in a context instance variable. We need access to a “context” object to send internet requests.

    public CategoriesRequest(Context context) {
        this.context = context;
    }

    public void getCategories(Callback activity) {

        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://resto.mprog.nl/categories", null, this, this);
        queue.add(jsonObjectRequest);
    }
}
