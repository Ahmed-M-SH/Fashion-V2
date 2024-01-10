package com.example.fashion.Adapter;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fashion.Domain.Promotion;
import com.example.fashion.R;

import java.util.List;

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.ViewHolder> {

    private List<Promotion> items;

    public PromotionAdapter(List<Promotion> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public PromotionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_fragment, parent, false);
        return new PromotionAdapter.ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull PromotionAdapter.ViewHolder holder, int position) {
        String imageUrl = items.get(position).getImage();

        // Load image into the ImageView using Glide
        Glide.with(holder.imageView.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.pic1)
                .into(holder.imageView);

        holder.discountTxt.setText("تخفيضات تصل ل "+items.get(position).getDiscountRate()+"%");
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView discountTxt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            discountTxt = (TextView) itemView.findViewById(R.id.discountTxt);
        }
    }
}
