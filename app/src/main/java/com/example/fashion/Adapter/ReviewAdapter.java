package com.example.fashion.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashion.Domain.ProductDetail;
import com.example.fashion.R;
import com.like.LikeButton;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.Viewholder>{
    ProductDetail items;
    Context context;


    public ReviewAdapter(ProductDetail items) {
        this.items = items;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_review,parent, false);
        context = parent.getContext();
        return new Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.reviewTxt.setText(items.getReview().get(position).getReviewText());
        holder.userReview.setText(items.getReview().get(position).getUser());
        holder.dataReview.setText(items.getReview().get(position).getReviewDate());
        holder.numb_like.setText(""+items.getReview().get(position).getLikesCount());
        if(items.getReview().get(position).getIsLiked())
            holder.fovortieBtn.setLiked(true);

    }

    @Override
    public int getItemCount() {
        return this.items.getReview().size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView userReview,dataReview,reviewTxt, numb_like;
        LikeButton fovortieBtn;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            userReview=itemView.findViewById(R.id.userReview);
            dataReview=itemView.findViewById(R.id.dataReview);
            reviewTxt=itemView.findViewById(R.id.reviewTxt);
            fovortieBtn = itemView.findViewById(R.id.liketieBtn);
            numb_like = itemView.findViewById(R.id.nump_like);

        }
    }
}
