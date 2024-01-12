package com.example.fashion.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashion.Domain.ProductDetail;
import com.example.fashion.Domain.ReviewLikes;
import com.example.fashion.Domain.UserAuthentication;
import com.example.fashion.Helper.RetrofitClient;
import com.example.fashion.Helper.TinyDB;
import com.example.fashion.R;
import com.like.LikeButton;
import com.like.OnLikeListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.Viewholder>{
    ProductDetail items;
    Context context;

    UserAuthentication auth;
    boolean isAuthent;
    TinyDB tinyDB;


    public ReviewAdapter(ProductDetail items,boolean isAuthent) {
        this.items = items;
        this.isAuthent = isAuthent;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_review,parent, false);
        context = parent.getContext();
        tinyDB = new TinyDB(context);
        return new Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.reviewTxt.setText(items.getReview().get(position).getReviewText());
        holder.userReview.setText(items.getReview().get(position).getUser());
        holder.dataReview.setText(items.getReview().get(position).getReviewDate());
        holder.numb_like.setText(""+items.getReview().get(position).getLikesCount());
        if(items.getReview().get(position).getIsLiked())
            holder.likeBtn.setLiked(true);
        int position2=position;

        holder.likeBtn.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                postReviewLikeRequest(items.getReview().get(position2).getId());
                holder.numb_like.setText(""+(items.getReview().get(position2).getLikesCount()+1));
            }
            @Override
            public void unLiked(LikeButton likeButton) {
                deleteReviewLikeRequest(items.getReview().get(position2).getId());
                holder.numb_like.setText(""+items.getReview().get(position2).getLikesCount());

            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.getReview().size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView userReview,dataReview,reviewTxt, numb_like;
        LikeButton likeBtn;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            userReview=itemView.findViewById(R.id.userReview);
            dataReview=itemView.findViewById(R.id.dataReview);
            reviewTxt=itemView.findViewById(R.id.reviewTxt);
            likeBtn = itemView.findViewById(R.id.liketieBtn);
            numb_like = itemView.findViewById(R.id.nump_like);

        }
    }


    private void postReviewLikeRequest(int reviewId){
        if (isAuthent){
        auth = tinyDB.getObject("userAuth", UserAuthentication.class);
        ReviewLikes reviewLikes = new ReviewLikes(reviewId);
        Call<ReviewLikes> call = RetrofitClient.getInstance().getServerDetail().postReviewLike(auth.getToken(),reviewLikes);
        call.enqueue(new Callback<ReviewLikes>() {
            @Override
            public void onResponse(Call<ReviewLikes> call, Response<ReviewLikes> response) {

            }

            @Override
            public void onFailure(Call<ReviewLikes> call, Throwable t) {

            }
        });
    }
    }

    private void deleteReviewLikeRequest(int reviewId){
        if (isAuthent){
            auth = tinyDB.getObject("userAuth", UserAuthentication.class);
            ReviewLikes reviewLikes = new ReviewLikes(reviewId);
            Call<ReviewLikes> call = RetrofitClient.getInstance().getServerDetail().deleteReviewLike(auth.getToken(),reviewId);
            call.enqueue(new Callback<ReviewLikes>() {
                @Override
                public void onResponse(Call<ReviewLikes> call, Response<ReviewLikes> response) {

                }

                @Override
                public void onFailure(Call<ReviewLikes> call, Throwable t) {

                }
            });
        }
    }
}



