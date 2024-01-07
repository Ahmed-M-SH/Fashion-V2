package com.example.fashion.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashion.Domain.NotificationDomain;
import com.example.fashion.R;

import java.util.List;
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.Viewholder>{
    private Context context;
    List<NotificationDomain> items;

    public NotificationAdapter(List<NotificationDomain> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public NotificationAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_notification,parent, false);
        context = parent.getContext();
        return new NotificationAdapter.Viewholder(inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.Viewholder holder, int position) {
        holder.txtMessage.setText(items.get(position).getText());
        holder.title_Message.setText(items.get(position).getTitle());
        holder.textDate.setText(items.get(position).getDateCreated());
        holder.textTime.setText(items.get(position).getTimeCreated());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView title_Message,txtMessage,textDate,textTime;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            title_Message = (TextView) itemView.findViewById(R.id.title_Message);
            txtMessage = (TextView) itemView.findViewById(R.id.txtMessage);
            textDate = itemView.findViewById(R.id.TextDate);
            textTime = itemView.findViewById(R.id.TextTime);
        }
    }
}
