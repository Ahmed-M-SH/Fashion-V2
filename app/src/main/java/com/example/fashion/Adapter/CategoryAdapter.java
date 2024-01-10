package com.example.fashion.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.fashion.Activity.CategoryActivity;
import com.example.fashion.Activity.ProductListActivity;
import com.example.fashion.Domain.Category;
import com.example.fashion.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Viewholder>{
    public CategoryAdapter(List<Category> items) {
        this.items = items;
    }
    List<Category> items;
    Context context;
    public CategoryAdapter(List<Category> items, int category) {
        this.items = items;
        this.category = category;
    }
    public int category;
    @NonNull
    @Override
    public CategoryAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_categorie,parent, false);
        context = parent.getContext();
        return new CategoryAdapter.Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.Viewholder holder, int position) {
        holder.titleTxt.setText(items.get(position).getName());
        int position2 = position;
        if (!items.get(position).getCategoryImage().isEmpty())
            Glide.with(holder.itemView.getContext()).load(items.get(position2).getCategoryImage()).into(holder.image);
        else {
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.pic1)
                    .transform(new GranularRoundedCorners(30,30,0,0))
                    .into(holder.image);
        }
        holder.constraintContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
            if (items.get(position2).getHave_children()){
                intent = new Intent(context, CategoryActivity.class);
                intent.putExtra("have_children",items.get(position2).getHave_children());
                    intent.putExtra("category",items.get(position2).getId());
            }
            else {
                intent = new Intent(context, ProductListActivity.class);
                intent.putExtra("category",items.get(position2).getId());
            }
            context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView titleTxt;
        ConstraintLayout constraintContainer;
        ImageView image;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            titleTxt = (TextView) itemView.findViewById(R.id.titleTxt);
            image = (ImageView) itemView.findViewById(R.id.image);
            constraintContainer = itemView.findViewById(R.id.constraintContainer);
        }
    }

    public void updateData(List<Category> newData) {
        items.clear();
        items.addAll(newData);
        notifyDataSetChanged();
    }
}
