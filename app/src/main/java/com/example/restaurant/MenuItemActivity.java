package com.example.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class MenuItemActivity extends AppCompatActivity {

    String choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toast.makeText(this , "MenuItemActivity" , Toast.LENGTH_SHORT).show();
        Intent intent = getIntent();
        MenuItem menuItem = (MenuItem) intent.getSerializableExtra("menuItem");

        TextView detailName = findViewById(R.id.detailName);
        TextView detailDescription = findViewById(R.id.detailText);
        TextView detailPrice = findViewById(R.id.detailPrice);
        ImageView detailImage = findViewById(R.id.detailImage);

        detailName.setText(menuItem.getName());
        detailDescription.setText(menuItem.getDescription());
        detailPrice.setText(menuItem.getPrice());
        String imageUrl;
        imageUrl = menuItem.getImageUrl();
        Picasso.with(getApplicationContext()).load(imageUrl).into(detailImage);
    }

}
