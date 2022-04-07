package com.example.food.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food.Domain.Cart;
import com.example.food.Domain.Product;
import com.example.food.Interface.ChangNumberItemsListener;
import com.example.food.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> {
    ArrayList<Cart> carts;

    private ChangNumberItemsListener changNumberItemsListener;

    public CardListAdapter(ArrayList<Cart> carts) {
        this.carts = carts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_card, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(carts.get(position).getProductDomain().getName());
        holder.feeEachItem.setText(String.valueOf(carts.get(position).getProductDomain().getPrice()));
        holder.totalEachItem.setText(String.valueOf(Math.round(carts.get(position).getProductDomain().getPrice()*carts.get(position).getQuantity()*100.0)/100.0));
        Picasso.get().load(carts.get(position).getProductDomain().getImage().getLink()).into(holder.pic);
        holder.num.setText(String.valueOf(carts.get(position).getQuantity()));

        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return carts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,feeEachItem,totalEachItem,addBtn,num;
        ImageView pic,plusItem,minusItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title2Txt);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            pic = itemView.findViewById(R.id.picCard);
            num=itemView.findViewById(R.id.numberItemTxt);
            plusItem=itemView.findViewById(R.id.plusItem);
            minusItem=itemView.findViewById(R.id.minusItem);

        }
    }
}

