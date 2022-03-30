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
import com.example.food.Activity.ShowDetailActivity;
import com.example.food.Domain.FoodDomain;
import com.example.food.Helper.ManagementCart;
import com.example.food.Interface.ChangNumberItemsListener;
import com.example.food.R;

import java.util.ArrayList;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> {
    ArrayList<FoodDomain> foodDomain;
private ManagementCart managementCart;
private ChangNumberItemsListener changNumberItemsListener;

    public CardListAdapter(ArrayList<FoodDomain> foodDomain, Context context,ChangNumberItemsListener changNumberItemsListener) {

        this.foodDomain = foodDomain;
        managementCart=new ManagementCart(context);
        this.changNumberItemsListener=changNumberItemsListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_card, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(foodDomain.get(position).getTitle());
        holder.feeEachItem.setText(String.valueOf(foodDomain.get(position).getFee()));
        holder.totalEachItem.setText(String.valueOf(Math.round(foodDomain.get(position).getNumberInCart()*foodDomain.get(position).getFee()*100.0)/100.0));
        holder.num.setText(String.valueOf(foodDomain.get(position).getNumberInCart()));

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(foodDomain.get(position).getPic(), "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.pic);

        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managementCart.plusNumberFood(foodDomain, position, new ChangNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changNumberItemsListener.changed();
                    }
                });
            }
        });

        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managementCart.minusNumberFood(foodDomain, position, new ChangNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changNumberItemsListener.changed();
                    }
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return foodDomain.size();
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

