package com.example.fashion.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fashion.Domain.OrderDetail;
import com.example.fashion.R;
import java.util.List;
public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.Viewholder>{
    public OrderDetailAdapter(List<OrderDetail> items) {
        this.items = items;
    }
    private List<OrderDetail> items;
    private Context context;

    @NonNull
    @Override
    public OrderDetailAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_order_list,parent, false);
        context = parent.getContext();
        return new OrderDetailAdapter.Viewholder(inflate);
    }
    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter.Viewholder holder, int position) {
        holder.TimeOrder.setText(items.get(position).getDate());
//        holder.itmeCountTxt.setText(items.get(position).get)
        holder.title_Order.setText("رقم# "+items.get(position).getId());
        holder.totalPaymentTxt.setText(items.get(position).getTotalPaid());
        holder.itmeCountTxt.setText(""+items.get(position).getItem_count());
        if (items.get(position).getIsProof())
            holder.stets_peyment.setText("تم تاكيد الطلب");
        else
            holder.stets_peyment.setText("لم يتم تاكيد الطلب");
        if (items.get(position).getIsDelivered())
            holder.deliverd_statusTxt.setText("تم التوصيل");
        else
            holder.deliverd_statusTxt.setText("لم يتم التوصيل بعد");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView title_Order,TimeOrder,stets_peyment,deliverd_statusTxt,totalPaymentTxt,itmeCountTxt,confirmedStatusTxt;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            title_Order = itemView.findViewById(R.id.title_Order);
            TimeOrder=itemView.findViewById(R.id.TimeOrder);
            stets_peyment = itemView.findViewById(R.id.stets_peyment);
            deliverd_statusTxt= itemView.findViewById(R.id.deliverd_statusTxt);
            totalPaymentTxt= itemView.findViewById(R.id.totalPaymentTxt);
            itmeCountTxt = itemView.findViewById(R.id.itmeCountTxt);
            confirmedStatusTxt = itemView.findViewById(R.id.confirmedStatusTxt);
        }
    }
}
