package com.example.restaurant;
import android.content.Context;
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
import static java.lang.String.valueOf;

public class MenuRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    Context context;
    Callback activity;
    String menuChosen;

    // Reports if an error occurred
    @Override
    public void onErrorResponse(VolleyError error) {
        String errorMessage = error.getMessage();
        activity.gotMenuCategoriesError("A network error occurred: " + errorMessage);
    }

    // Runs on response
    @Override
    public void onResponse(JSONObject response) {

        // Declares and initializes an ArrayList of MenuItems
        ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();

        // Declares a new JSONArray named 'items'
        JSONArray items;

        // For each JSONObject within the JSONArray 'items'
        // Gets all the fields of the JSONObject, and stores these in a MenuItem object
        // Stores the MenuItem object in an ArrayList of MenuItems
        try {
            items = response.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {

                JSONObject itemObject = items.getJSONObject(i);
                String name = itemObject.getString("name");
                String description = itemObject.getString("description");
                String imageUrl = itemObject.getString("image_url");
                String price = valueOf(itemObject.getInt("price"));
                String category = itemObject.getString("category");
                MenuItem menuItem = new MenuItem();
                menuItem.setName(name);
                menuItem.setDescription(description);
                menuItem.setImageUrl(imageUrl);
                menuItem.setPrice(price);
                menuItem.setCategory(category);
                menuItems.add(menuItem);
            }
        }

        // In case of an JSONException, makes a toast containing the error
        catch (JSONException e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }

        // Calls gotMenuCategories, passing the ArrayList of MenuItems as a parameter
        activity.gotMenuCategories(menuItems);
    }

    // Implements the Callback functionality
    public interface Callback {
        void gotMenuCategories(ArrayList<MenuItem> menuItems);
        void gotMenuCategoriesError(String message);
    }

    // Constructor of the class
    public MenuRequest(Context context, String choice) {
        this.context = context;
        menuChosen = choice;
    }

    // Implements the JsonObjectRequest
    public void getMenuCategories(Callback activity) {

        // Calls a newRequestQueue passing context as a parameter
        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);

        // In case the user chooses 'appetizers'
        if (menuChosen.equals("appetizers")) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://resto.mprog.nl/menu?category=appetizers", null, this, this);
            queue.add(jsonObjectRequest);
        }

        // In case the user chooses 'entrees'
        else if (menuChosen.equals("entrees")) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://resto.mprog.nl/menu?category=entrees", null, this, this);
            queue.add(jsonObjectRequest);
        }
    }
}