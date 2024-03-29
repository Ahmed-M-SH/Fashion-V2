package com.example.fashion.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.fashion.Domain.CartProduct;
import com.example.fashion.Domain.Favorite;
import com.example.fashion.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.Viewholder> {

    private Context context;
    private List<Favorite> items;
    private Set<Integer> selectedPositions = new HashSet<>();


    public FavoriteAdapter(List<Favorite> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public FavoriteAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_fovorite_item,parent, false);
        context = parent.getContext();
        return new FavoriteAdapter.Viewholder(inflate);    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.Viewholder holder, int position) {

        holder.item_title_name.setText(items.get(position).getProduct().getName());
        holder.price_item.setText("$"+items.get(position).getProduct().getPrice());
        if (!items.get(position).getProduct().getImage().isEmpty())
            Glide.with(holder.itemView.getContext()).load(items.get(position).getProduct().getImage().get(0)).into(holder.items_imagesView);
        else {
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.pic1)
                    .transform(new GranularRoundedCorners(30,30,0,0))
                    .into(holder.items_imagesView);
        }

        holder.checkBox.setChecked(selectedPositions.contains(position));
        int position2 = position;

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedPositions.contains(position2)) {
                    selectedPositions.remove(position2);
                } else {
                    selectedPositions.add(position2);
                }
                notifyDataSetChanged();
            }
        });
        holder.deletePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                items.remove(position);
                notifyItemRemoved(position2);
                notifyItemRangeChanged(position2, getItemCount());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void selectAllItems(boolean selectAll) {
        if (selectAll) {
            for (int i = 0; i < items.size(); i++) {
                selectedPositions.add(i);
            }
        } else {
            selectedPositions.clear();
        }
        notifyDataSetChanged();
    }

    public List<Favorite> getSelectedItems() {
        List<Favorite> selectedItems = new ArrayList<>();
        for (int position : selectedPositions) {
            selectedItems.add(items.get(position));
        }
        return selectedItems;
    }

    public void deleteSelectedItems() {
//        List<Favorite> selectedItems = new ArrayList<>();
        for (int position : selectedPositions) {
//            selectedItems.add(items.get(position));
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
            notifyDataSetChanged();
        }
    }


    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView items_imagesView,deletePic;
        TextView item_title_name,price_item;
        CheckBox checkBox;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            items_imagesView = (ImageView) itemView.findViewById(R.id.items_imageView);
            deletePic = (ImageView) itemView.findViewById(R.id.deletePic);
            item_title_name = (TextView) itemView.findViewById(R.id.item_titl_name);
            price_item = (TextView) itemView.findViewById(R.id.price_itme);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBoxfovorite);

        }
    }
}
