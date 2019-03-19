package com.example.restaurant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements MenuRequest.Callback {

    String choice;
    ArrayList<MenuItem> menuItems = new ArrayList<>();

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        choice = (String) intent.getSerializableExtra("choice");

        MenuRequest x = new MenuRequest(this, choice);
        x.getMenuCategories(this);
    }

    @Override
    public void gotMenuCategories(ArrayList<MenuItem> menuItems) {
        Toast.makeText(this, "Message", Toast.LENGTH_LONG).show();
        ListView menuListView = findViewById(R.id.menuListView);
        ArrayAdapter MenuItemAdapter = new MenuItemAdapter(this, R.layout.menu_row, menuItems);
        menuListView.setAdapter(MenuItemAdapter);

        AdapterView.OnItemClickListener listViewListener = new ClickViewListener();
        menuListView.setOnItemClickListener(listViewListener);
    }

    private class ClickViewListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            MenuItem clickedDish = (MenuItem) parent.getItemAtPosition(position);
            Intent intent = new Intent(MenuActivity.this, MenuItemActivity.class);
            intent.putExtra("menuItem", clickedDish);
            startActivity(intent);
        }
    }

    @Override
    public void gotMenuCategoriesError(String message) {

    }

}
