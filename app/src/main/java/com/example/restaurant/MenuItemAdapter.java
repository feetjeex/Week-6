package com.example.restaurant;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MenuItemAdapter extends ArrayAdapter<MenuItem> {

    private ArrayList<MenuItem> menuItems;
    String name;
    String price;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // If no convertView exists, creates a new one
        if( convertView == null ){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.menu_row, parent, false);
        }

        //Declares and assigns a MenuItem object named pointer and fills it with data
        MenuItem pointer = menuItems.get(position);

        // Assigns the UI elements
        // Fills the UI elements with text and an image from the pointer MenuItem
        // Returns the filled convertView
        TextView menuName = convertView.findViewById(R.id.menuName);
        name = pointer.getName();
        menuName.setText(name);
        TextView menuPrice = convertView.findViewById(R.id.menuPrice);
        price = pointer.getPrice();
        menuPrice.setText("$ " + price);
        ImageView menuImage = convertView.findViewById(R.id.menuImage);
        String imageUrl;
        imageUrl = pointer.getImageUrl();
        Picasso.with(getContext()).load(imageUrl).into(menuImage);
        return convertView;
    }

    // Constructor of the MenuItemAdapter
    public MenuItemAdapter(Context context, int resource, ArrayList<MenuItem> objects) {
        super(context, resource, objects);
        menuItems = objects;
    }
}
