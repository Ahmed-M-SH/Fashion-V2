package com.example.fashion.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.fashion.Domain.Category;
import com.example.fashion.R;

import java.util.List;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.Viewholder>{

    public HomeCategoryAdapter(List<Category> items) {
        this.items = items;
//        this.categoryList = new ArrayList<Category>();

    }

    List<Category> items;
    Context context;
//    List<Category> categoryList;
    @NonNull
    @Override
    public HomeCategoryAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_categorie,parent, false);
        context = parent.getContext();
        return new HomeCategoryAdapter.Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCategoryAdapter.Viewholder holder, int position) {
        holder.titleTxt.setText(items.get(position).getName());

        if (!items.get(position).getCategoryImage().isEmpty())
            Glide.with(holder.itemView.getContext()).load(items.get(position).getCategoryImage()).into(holder.image);
        else {
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.pic1)
                    .transform(new GranularRoundedCorners(30,30,0,0))
                    .into(holder.image);
        }    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView titleTxt;
        ImageView image;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            titleTxt = (TextView) itemView.findViewById(R.id.titleTxt);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }

}
