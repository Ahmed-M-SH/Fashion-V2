package com.example.fashion.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fashion.Models.ListData;
import com.example.fashion.R;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<ListData> {
    public ListAdapter(@NonNull Context context, ArrayList<ListData> dataArrayList) {
        super(context, R.layout.viewholder_fovorite_item, dataArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        ListData listData = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.viewholder_fovorite_item, parent, false);
        }

        ImageView listImage = view.findViewById(R.id.items_imageView);
        TextView listName = view.findViewById(R.id.item_titl_name);
        TextView priceList = view.findViewById(R.id.price_itme);

//        listImage.setImageResource(listData.getImage());
//        listName.setText(listData.getName());
//        priceList.setText(listData.getPrice());

        return view;
    }
}