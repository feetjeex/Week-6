package com.example.restaurant;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.restaurant.CategoriesRequest;
import com.example.restaurant.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static java.lang.String.valueOf;

public class MenuRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    private static final String TAG = "MainActivity";

    Context context;
    JSONArray items;
    Callback activity;
    String name;
    String description;
    String imageUrl;
    String price;
    String category;
    String menuChosen;

    @Override
    public void onErrorResponse(VolleyError error) {
        String errorMessage = error.getMessage();
        activity.gotMenuCategoriesError("A network error occurred: " + errorMessage);

    }

    @Override
    public void onResponse(JSONObject response) {
        ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
        items = new JSONArray();
        try {
            items = response.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {

                JSONObject itemObject = items.getJSONObject(i);
                name = itemObject.getString("name");
                description = itemObject.getString("description");
                imageUrl = itemObject.getString("image_url");
                price = valueOf(itemObject.getInt("price"));
                category = itemObject.getString("category");
                MenuItem menuItem = new MenuItem();
                menuItem.setName(name);
                menuItem.setDescription(description);
                menuItem.setImageUrl(imageUrl);
                menuItem.setPrice(price);
                menuItem.setCategory(category);
                menuItems.add(menuItem);
            }
        }
        catch (JSONException e) {

            Log.d(TAG, "onResponse: " + e.toString());
        }

        activity.gotMenuCategories(menuItems);
    }

    public interface Callback {
        void gotMenuCategories(ArrayList<MenuItem> menuItems);
        void gotMenuCategoriesError(String message);
    }

    public MenuRequest(Context context, String choice) {
        this.context = context;
        menuChosen = choice;
    }

    public void getMenuCategories(Callback activity) {

        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);

        if (menuChosen.equals("appetizers")) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://resto.mprog.nl/menu?category=appetizers", null, this, this);
            queue.add(jsonObjectRequest);
        }

        else if (menuChosen.equals("entrees")) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://resto.mprog.nl/menu?category=entrees", null, this, this);
            queue.add(jsonObjectRequest);
        }
    }
}