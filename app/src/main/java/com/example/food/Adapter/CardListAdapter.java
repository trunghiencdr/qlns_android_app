package com.example.food.Adapter;

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
import com.example.food.Domain.ProductDomain;
import com.example.food.Interface.ChangNumberItemsListener;
import com.example.food.R;

import java.util.ArrayList;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> {
    ArrayList<ProductDomain> productDomains;
    private ChangNumberItemsListener changNumberItemsListener;

    public CardListAdapter(ArrayList<ProductDomain> productDomains) {
        this.productDomains = productDomains;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_card, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(productDomains.get(position).getName());
        holder.feeEachItem.setText(String.valueOf(productDomains.get(position).getPrice()));
        holder.totalEachItem.setText(String.valueOf(Math.round(productDomains.get(position).getPrice()*100.0)/100.0));
        holder.num.setText(String.valueOf(1));

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(productDomains.get(position).getImages().get(0).getLink(), "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.pic);

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
        return productDomains.size();
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

