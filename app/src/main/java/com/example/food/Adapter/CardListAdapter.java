package com.example.food.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food.Activity.CartListActivity;
import com.example.food.Activity.ShowDetailActivity;
import com.example.food.Api.Api;
import com.example.food.Domain.Cart;
import com.example.food.Domain.Product;
import com.example.food.Domain.Response.CartResponse;
import com.example.food.Interface.ChangNumberItemsListener;
import com.example.food.Listener.CartResponseListener;
import com.example.food.Listener.DeleteCartResponseListener;
import com.example.food.Listener.InsertCartResponseListener;
import com.example.food.R;
import com.example.food.dto.CartDTO;
import com.example.food.model.User;
import com.example.food.util.AppUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> {
    ArrayList<Cart> carts=new ArrayList<>();
    Api api;
    Context context;
    private ChangNumberItemsListener changNumberItemsListener;

    public CardListAdapter(ArrayList<Cart> carts, Context context) {
        this.carts = carts;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_card, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(carts.get(position).getProductDomain().getName());
        holder.feeEachItem.setText(String.valueOf(carts.get(position).getProductDomain().getPrice()));
        holder.totalEachItem.setText(String.valueOf(Math.round(carts.get(position).getProductDomain().getPrice()*carts.get(position).getQuantity()*100.0)/100.0));
        Picasso.get().load(carts.get(position).getProductDomain().getImage().getLink()).into(holder.pic);
        holder.num.setText(String.valueOf(carts.get(position).getQuantity()));


        api = new Api(context);
        User user = AppUtils.getAccount(context.getSharedPreferences(AppUtils.ACCOUNT, Context.MODE_PRIVATE));

        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity=carts.get(position).getQuantity();
//                holder.num.setText(String.valueOf(quantity++));
                quantity++;
                //update cart
                CartDTO cartDTO=new CartDTO(Integer.parseInt(user.getId()+"")
                        ,Integer.parseInt(carts.get(position).getProductDomain().getProductId()+"")
                        ,quantity);
                api.updateCart(updateCartResponseListener,cartDTO);
                setData();
            }
        });

        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity=carts.get(position).getQuantity();
                if(quantity>1){
                    //holder.num.setText(String.valueOf(quantity--));
                    quantity--;
                    //update cart
                    CartDTO cartDTO=new CartDTO(Integer.parseInt(user.getId()+"")
                            ,Integer.parseInt(carts.get(position).getProductDomain().getProductId()+"")
                            ,quantity);
                    api.updateCart(updateCartResponseListener,cartDTO);

                }
                if(quantity==1){
                    //delete cart
                    api.deleteCartByUserIdAndProductId(deleteCartResponseListener,Integer.parseInt(user.getId()+"")
                            ,Integer.parseInt(carts.get(position).getProductDomain().getProductId()+""));
                }
                setData();

            }
        });
    }

    private void setData() {
        User user = AppUtils.getAccount(context.getSharedPreferences(AppUtils.ACCOUNT, Context.MODE_PRIVATE));
        api.getCartsByUserId(cartResponseListener,user.getId());

    }

    private final CartResponseListener cartResponseListener = new CartResponseListener() {
        @Override
        public void didFetch(ArrayList<Cart> response, String message) {
            ArrayList<Cart> carts1=new ArrayList<>();
            if (response != null) {
                for (int i = 0; i < response.size(); i++) {
                    carts1.add(response.get(i));
                }
            }
            carts=carts1;
            for (int i = 0; i < carts.size(); i++) {
                System.out.println(carts.get(i).toString());
            }
            notifyDataSetChanged();
        }

        @Override
        public void didError(String message) {
            Log.d("cart",message);
        }
    };

    private final InsertCartResponseListener updateCartResponseListener=new InsertCartResponseListener() {
        @Override
        public void didFetch(CartResponse response, String message) {
            Toast.makeText(context, "Call api update cart success"+message.toString(),Toast.LENGTH_SHORT).show();
        }

        @Override
        public void didError(String message) {
            Toast.makeText(context,"Call api error"+message.toString(),Toast.LENGTH_SHORT).show();
        }
    };

    private final DeleteCartResponseListener deleteCartResponseListener=new DeleteCartResponseListener() {
        @Override
        public void didFetch(String response, String message) {
            Toast.makeText(context, "Call api delete cart success"+message.toString(),Toast.LENGTH_SHORT).show();
        }

        @Override
        public void didError(String message) {
            Toast.makeText(context,"Call api error"+message.toString(),Toast.LENGTH_SHORT).show();
        }
    };

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

