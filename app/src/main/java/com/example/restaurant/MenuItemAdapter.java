package com.example.restaurant;


import android.content.Context;
import android.util.Log;
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

    private static final String TAG = "MainActivity";

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if( convertView == null ){
            //We must create a View:
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.menu_row, parent, false);
        }

        //Here we can do changes to the convertView, such as set a text on a TextView
        //or an image on an ImageView.
        MenuItem pointer = menuItems.get(position);

        TextView menuName = convertView.findViewById(R.id.menuName);
        name = pointer.getName();
        menuName.setText(name);
        Log.d(TAG, "getView: " + name);

        TextView menuPrice = convertView.findViewById(R.id.menuPrice);
        price = pointer.getName();
        menuPrice.setText(price);

        ImageView menuImage = convertView.findViewById(R.id.menuImage);
        String imageUrl;
        imageUrl = pointer.getImageUrl();
        Picasso.with(getContext()).load(imageUrl).into(menuImage);
        return convertView;
    }

    public MenuItemAdapter(Context context, int resource, ArrayList<MenuItem> objects) {
        super(context, resource, objects);
        menuItems = objects;
    }
}
