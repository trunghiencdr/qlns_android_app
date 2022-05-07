package com.example.food.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.Domain.Order;
import com.example.food.Domain.Product;
import com.example.food.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder>{
    ArrayList<Order> orders;

    public OrderListAdapter(ArrayList<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ordered, parent, false);
        return new OrderListAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_order_id.setText(orders.get(position).getId()+"");

        //String newstring = orders.get(position).getCreateAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String stringDate = new SimpleDateFormat("yyyy-MM-dd").format(orders.get(position).getCreateAt());
        holder.tv_date.setText(stringDate);
        if(orders.get(position).getDiscount()!=null){
            holder.tv_discount.setText(orders.get(position).getDiscount().getId());
        }
        if(orders.get(position).getState()!=null){
            holder.tv_state.setText(orders.get(position).getState());
        }

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_order_id,tv_date,tv_discount,tv_state;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_order_id = itemView.findViewById(R.id.tv_order_id);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_discount = itemView.findViewById(R.id.tv_discount);
            tv_state = itemView.findViewById(R.id.tv_state);
        }
    }
}
