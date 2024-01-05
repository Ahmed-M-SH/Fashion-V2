package com.example.navigationdrawer.Adapter;

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
import com.example.navigationdrawer.Domain.Category;
import com.example.navigationdrawer.Domain.ProductResult;
import com.example.navigationdrawer.Domain.SubCategory;
import com.example.navigationdrawer.R;

import java.util.ArrayList;
import java.util.List;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.Viewholder>{

    public HomeCategoryAdapter(List<Category> items) {
        this.items = items;
//        this.categoryList = new ArrayList<Category>();
        this.categoryList = getAllSubCategories(items);
    }

    List<Category> items;
    Context context;
    List<Category> categoryList;
    @NonNull
    @Override
    public HomeCategoryAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_categorie,parent, false);
        context = parent.getContext();
        return new HomeCategoryAdapter.Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCategoryAdapter.Viewholder holder, int position) {
        holder.titleTxt.setText(categoryList.get(position).getName());

        if (!categoryList.get(position).getCategoryImage().isEmpty())
            Glide.with(holder.itemView.getContext()).load(categoryList.get(position).getCategoryImage()).into(holder.image);
        else {
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.pic1)
                    .transform(new GranularRoundedCorners(30,30,0,0))
                    .into(holder.image);
        }    }

    @Override
    public int getItemCount() {
        return categoryList.size();
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

    private List<Category> getAllSubCategories(List<Category> categories) {
        List<Category> allSubCategories = new ArrayList<>();
        for (Category category : categories) {
            allSubCategories.add(category);
            if (category.getSubCategory() != null && !category.getSubCategory().isEmpty()) {
                allSubCategories.addAll(getAllSubCategories(category.getSubCategory()));
            }
        }
        return allSubCategories;
    }
}
