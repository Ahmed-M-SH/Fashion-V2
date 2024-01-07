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
import com.example.fashion.Helper.ChangeNumberItemsListener;
import com.example.fashion.Helper.ManagementCart;
import com.example.fashion.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Viewholder> {

    private List<CartProduct> items;
    private Context context;
    public ManagementCart managmentCart;

    private Set<Integer> selectedPositions = new HashSet<>();
    public CartAdapter(List<CartProduct> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CartAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart,parent, false);
        context = parent.getContext();
        managmentCart = new ManagementCart(context);
        return new Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.Viewholder holder, int position) {
        int position2 = position;
        holder.qtyTxt.setText(""+items.get(position).getQty());
        holder.titleTxt.setText(""+items.get(position).getName());
        holder.feeEachitem.setText("$"+items.get(position).getNewPrice());
        holder.totalEachItem.setText(""+items.get(position).getTotal());



        holder.checkBox.setChecked(selectedPositions.contains(position));

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedPositions.contains(position)) {
                    selectedPositions.remove(position);
                } else {
                    selectedPositions.add(position);
                }
                notifyDataSetChanged();
            }
        });




        if (!items.get(position).getImage().isEmpty())
            Glide.with(holder.itemView.getContext())
                    .load(items.get(position).getImage())
                    .into(holder.pic);
        else {
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.pic1)
                    .transform(new GranularRoundedCorners(30,30,0,0))
                    .into(holder.pic);
        }

        holder.plusCartBtn.setOnClickListener(view -> managmentCart.PlusNumberItem(items, position2, new ChangeNumberItemsListener() {
                    @Override
                    public void change() {
                        holder.qtyTxt.setText(""+items.get(position2).getQty());
                        holder.totalEachItem.setText(""+items.get(position2).getTotal());
                    }

                    @Override
                    public void itemRemoved(int position) {

                    }
                }));
                holder.minusCartBtn.setOnClickListener(view -> managmentCart.minusNumberItems(items, position2, new ChangeNumberItemsListener() {
                    @Override
                    public void change() {
                        if (items.get(position2) != null) {
                            holder.qtyTxt.setText("" + items.get(position2).getQty());
                            holder.totalEachItem.setText("" + items.get(position2).getTotal());
                        }
                    }

                    @Override
                    public void itemRemoved(int position) {
//                        items.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, getItemCount());
                    }
                }));

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

    public List<CartProduct> getSelectedItems() {
        List<CartProduct> selectedItems = new ArrayList<>();
        for (int position : selectedPositions) {
            selectedItems.add(items.get(position));
        }
        return selectedItems;
    }
    public Double getTotal() {
        List<CartProduct> selectedItems = new ArrayList<>();
        Double total = 0.0;
        for (int position : selectedPositions) {
//            selectedItems.add(items.get(position));
            total += items.get(position).getTotal();
        }
        return total;
    }


    public class Viewholder extends  RecyclerView.ViewHolder {
        ImageView pic;
        CheckBox checkBox;
        TextView feeEachitem,titleTxt,qtyTxt,totalEachItem,plusCartBtn,minusCartBtn;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            pic = (ImageView) itemView.findViewById(R.id.pic);
            feeEachitem = (TextView) itemView.findViewById(R.id.feeEachitem);
            titleTxt = (TextView) itemView.findViewById(R.id.titleTxt);
            qtyTxt = (TextView) itemView.findViewById(R.id.qtyTxt);
            totalEachItem = (TextView) itemView.findViewById(R.id.totalEachItem);
            plusCartBtn = itemView.findViewById(R.id.pludCartBtn);
            minusCartBtn = itemView.findViewById(R.id.minusCartBtn);
            checkBox = itemView.findViewById(R.id.checkBox);

        }
    }

}
