package com.example.navigationdrawer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.navigationdrawer.Domain.ProductResult;
import com.example.navigationdrawer.R;

public class HomeProductAdapter extends RecyclerView.Adapter<HomeProductAdapter.Viewholder>{
    ProductResult items;
    Context context;
    @NonNull
    @Override
    public HomeProductAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_pop_list,parent, false);
        context = parent.getContext();
        return new Viewholder(inflate);
    }

    public HomeProductAdapter(ProductResult items) {
        this.items = items;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeProductAdapter.Viewholder holder, int position) {
        holder.titleTxt.setText(""+items.getResults().get(position).getName());
        holder.feeTxt.setText("$"+items.getResults().get(position).getNew_price());
        holder.ScoreTxt.setText(""+items.getResults().get(position).getRate());
        holder.reviewTxt.setText(""+items.getResults().get(position).getReview());
//        if (items.getResults().get(position).getImage().get(0) != null)

        if (items.getResults().get(position).getImage().size() > 0)
        Glide.with(holder.itemView.getContext()).load(items.getResults().get(position).getImage().get(0)).into(holder.pic);
        else {
            Glide.with(holder.itemView.getContext())
                .load(R.drawable.pic1)
                .transform(new GranularRoundedCorners(30,30,0,0))
                .into(holder.pic);
        }
        holder.itemView.setOnClickListener(view -> {
//            Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
//            intent.putExtra("product_id", items.getResults().get(position).getId());
//            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.getResults().size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView titleTxt,feeTxt,ScoreTxt,reviewTxt;
        ImageView pic;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            titleTxt = (TextView) itemView.findViewById(R.id.titleTxt);
            feeTxt = (TextView) itemView.findViewById(R.id.feeTxt);
            ScoreTxt = (TextView) itemView.findViewById(R.id.scoreTxt);
            reviewTxt = (TextView) itemView.findViewById(R.id.reviewTxt);
            pic = (ImageView) itemView.findViewById(R.id.pic);
        }
    }
}
