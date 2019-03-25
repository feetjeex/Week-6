package com.example.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class MenuItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Gets the user selected MenuItem using an Intent
        Intent intent = getIntent();
        MenuItem menuItem = (MenuItem) intent.getSerializableExtra("menuItem");

        // Assignment of the UI elements
        TextView detailName = findViewById(R.id.detailName);
        TextView detailDescription = findViewById(R.id.detailText);
        TextView detailPrice = findViewById(R.id.detailPrice);
        ImageView detailImage = findViewById(R.id.detailImage);

        // Sets the fields of the UI elements using data from the selected menuItem
        detailName.setText(menuItem.getName());
        detailDescription.setText(menuItem.getDescription());
        detailPrice.setText("$ " + menuItem.getPrice());
        String imageUrl;

        // Uses Picasso to load the Image of the selected menuItem
        imageUrl = menuItem.getImageUrl();
        Picasso.with(getApplicationContext()).load(imageUrl).into(detailImage);
    }
}
