package com.example.restaurant;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.widget.Toast;

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

        // If an error occurs, calls the gotCategoriesError method and passes it the VolleyError error as a parameter
        String errorMessage = error.getMessage();
        activity.gotCategoriesError("A network error occurred: " + errorMessage);
    }

    @Override
    public void onResponse(JSONObject response) {

        // Declares a new ArrayList of Strings
        ArrayList<String> arrayList = new ArrayList<String>();
        categories = new JSONArray();

        // For each Element in the JSONArray categories
        // Adds the String at position i to the ArrayList of Strings arrayList
        try {
            categories = response.getJSONArray("categories");
            for (int i = 0; i < categories.length(); i++) {
                arrayList.add((i), categories.getString(i));
            }
        }

        catch (JSONException e) {
            // In case of a JSONException, performs a Toast containing the Exception
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }

        // Calls the gotCategories method, passing the ArrayList arrayList as a parameter
        activity.gotCategories(arrayList);
    }

    // Implements the Callback functionality
    public interface Callback {
        void gotCategories(ArrayList<String> categories);
        void gotCategoriesError(String message);
    }

    // Constructor of the class
    public CategoriesRequest(Context context) {
        this.context = context;
    }

    // Implements the JSONObject request
    public void getCategories(Callback activity) {

        // Calls a newRequestQueue passing context as a parameter
        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://resto.mprog.nl/categories", null, this, this);
        queue.add(jsonObjectRequest);
    }
}
