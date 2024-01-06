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
import com.example.fashion.Domain.CartProduct;
import com.example.fashion.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Viewholder> {

    private List<CartProduct> items;
    private Context context;

    public CartAdapter(List<CartProduct> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CartAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart,parent, false);
        context = parent.getContext();
        return new Viewholder(inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.Viewholder holder, int position) {
        holder.qtyTxt.setText(""+items.get(position).getQty());
        holder.titleTxt.setText(""+items.get(position).getName());
        holder.feeEachitem.setText("$"+items.get(position).getNewPrice());
        holder.totalEachItem.setText(""+items.get(position).getTotal());
        if (!items.get(position).getImage().isEmpty())
            Glide.with(holder.itemView.getContext()).load(items.get(position).getImage()).into(holder.pic);
        else {
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.pic1)
                    .transform(new GranularRoundedCorners(30,30,0,0))
                    .into(holder.pic);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends  RecyclerView.ViewHolder {
        ImageView pic;
        TextView feeEachitem,titleTxt,qtyTxt,totalEachItem;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            pic = (ImageView) itemView.findViewById(R.id.pic);
            feeEachitem = (TextView) itemView.findViewById(R.id.feeEachitem);
            titleTxt = (TextView) itemView.findViewById(R.id.titleTxt);
            qtyTxt = (TextView) itemView.findViewById(R.id.qtyTxt);
            totalEachItem = (TextView) itemView.findViewById(R.id.totalEachItem);
        }
    }
}
