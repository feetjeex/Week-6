package com.example.restaurant;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity implements CategoriesRequest.Callback {

    Cursor cursor;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        CategoriesRequest x = new CategoriesRequest(this);
        x.getCategories(this);
        //Toast.makeText(this , "Started" , Toast.LENGTH_SHORT).show();

    }

    @Override
    public void gotCategories(ArrayList<String> categories) {
        //Toast.makeText(this , categories.get(0) , Toast.LENGTH_LONG).show();

        // Setting the ListView categoriesListView
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories);
        ListView categoriesListView = findViewById(R.id.categoriesListView);
        categoriesListView.setAdapter(categoriesAdapter);

        AdapterView.OnItemClickListener listViewListener = new ClickViewListener();
        categoriesListView.setOnItemClickListener(listViewListener);
    }

    private class ClickViewListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            String clickedCategory = (String) parent.getItemAtPosition(position);
            Intent intent = new Intent(CategoriesActivity.this, MenuActivity.class);
            Log.d(TAG, "clickedCategory = " + clickedCategory);
            intent.putExtra("choice", clickedCategory);
            startActivity(intent);
        }
    }

    @Override
    public void gotCategoriesError(String message) {
        Toast.makeText(this, "Internet problem.", Toast.LENGTH_LONG).show();
    }
}
