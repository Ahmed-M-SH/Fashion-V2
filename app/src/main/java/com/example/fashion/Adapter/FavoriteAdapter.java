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
import com.example.fashion.Domain.Favorite;
import com.example.fashion.R;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.Viewholder> {

    private Context context;
    private List<Favorite> items;

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

    }

    @Override
    public int getItemCount() {
        return items.size();
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
