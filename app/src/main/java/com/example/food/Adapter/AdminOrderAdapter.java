package com.example.food.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.Activity.ShowDetailActivity;
import com.example.food.Api.Api;
import com.example.food.Domain.Cart;
import com.example.food.Domain.Order;
import com.example.food.Domain.Product;
import com.example.food.Domain.Response.CartResponse;
import com.example.food.Listener.InsertCartResponseListener;
import com.example.food.R;
import com.example.food.dto.CartDTO;
import com.example.food.feature.home.HomeViewModel;
import com.example.food.model.User;
import com.example.food.util.AppUtils;
import com.squareup.picasso.Picasso;

import java.sql.SQLException;

public class AdminOrderAdapter extends ListAdapter<Order, AdminOrderAdapter.OrderViewHolder> {

    public AdminOrderAdapter( @NonNull DiffUtil.ItemCallback<Order> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_state_item, null);
        return new OrderViewHolder(
                view);
    }


    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.bind(getItem(position));

    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView txtOrderId, txtOrderState, txtOrderDate, txtOrderUser;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            txtOrderId = itemView.findViewById(R.id.text_view_id_order);
            txtOrderState = itemView.findViewById(R.id.text_view_state_order);
            txtOrderDate = itemView.findViewById(R.id.text_view_date_order);
            txtOrderUser = itemView.findViewById(R.id.text_view_user_order);


        }

        public void bind(Order item)  {
            txtOrderId.setText("ID: " + item.getId());
            txtOrderState.setText("Trạng thái: " + item.getState());
            txtOrderUser.setText("Người đặt: " + item.getUser().getName());
            txtOrderDate.setText(AppUtils.formatDate(item.getCreateAt(), "dd-MM-yyyy hh:mm:ss"));
        }
    }

}
