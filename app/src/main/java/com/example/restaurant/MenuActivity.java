package com.example.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements MenuRequest.Callback {

    String choice;
    ArrayList<MenuItem> menuItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Gets the users choice using an Intent
        Intent intent = getIntent();
        choice = (String) intent.getSerializableExtra("choice");

        // Constructs a new MenuRequest and calls getMenuCategories on this MenuRequest
        MenuRequest x = new MenuRequest(this, choice);
        x.getMenuCategories(this);
    }

    @Override
    public void gotMenuCategories(ArrayList<MenuItem> menuItems) {

        // If the MenuItems are received, sets the MenuItemAdapter to the ListView menuListView
        ListView menuListView = findViewById(R.id.menuListView);
        ArrayAdapter MenuItemAdapter = new MenuItemAdapter(this, R.layout.menu_row, menuItems);
        menuListView.setAdapter(MenuItemAdapter);

        // Sets an OnItemClickListener to the ListView menuListView
        AdapterView.OnItemClickListener listViewListener = new ClickViewListener();
        menuListView.setOnItemClickListener(listViewListener);
    }

    // Implements the OnItemClickListener
    private class ClickViewListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // Once the user clicks on a menuItem, transfers the user to that particular MenuItemActivity
            // Puts the selected menuItem into the intent
            MenuItem clickedDish = (MenuItem) parent.getItemAtPosition(position);
            Intent intent = new Intent(MenuActivity.this, MenuItemActivity.class);
            intent.putExtra("menuItem", clickedDish);
            startActivity(intent);
        }
    }

    // Shows a toast to the user if an error is encountered during the downloading of the MenuItems
    @Override
    public void gotMenuCategoriesError(String message) {
        Toast.makeText(this, "Network Error: Unable to download data.", Toast.LENGTH_LONG).show();
    }
}
