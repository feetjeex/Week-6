package com.example.restaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity implements CategoriesRequest.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        // Creates a new CategoriesRequest object, and calls the getCategories method on it
        CategoriesRequest x = new CategoriesRequest(this);
        x.getCategories(this);
    }

    @Override
    public void gotCategories(ArrayList<String> categories) {

        // If the ArrayList categories is received, sets the ArrayAdapter to the ListView categoriesListView
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories);
        ListView categoriesListView = findViewById(R.id.categoriesListView);
        categoriesListView.setAdapter(categoriesAdapter);

        // Sets an OnItemClickListener to the ListView categoriesListView
        AdapterView.OnItemClickListener listViewListener = new ClickViewListener();
        categoriesListView.setOnItemClickListener(listViewListener);
    }

    // Implements the OnItemClickListener
    private class ClickViewListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // If the user clicks on one of the categories in the ListView
            // Saves the users choice as a String and transfers the user to the next activity using Intent
            String clickedCategory = (String) parent.getItemAtPosition(position);
            Intent intent = new Intent(CategoriesActivity.this, MenuActivity.class);
            intent.putExtra("choice", clickedCategory);
            startActivity(intent);
        }
    }

    @Override
    public void gotCategoriesError(String message) {

        // In case an error occurs during the downloading of the categories
        Toast.makeText(this, "Internet problem.", Toast.LENGTH_LONG).show();
    }
}
